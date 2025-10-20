#!/usr/bin/env bash
set -euo pipefail

DOMAIN=${1:-"api.tomorrow.io"}
PORT=${2:-443}
TMP_DIR=$(mktemp -d)

echo "🔍 Fetching certificate chain for $DOMAIN:$PORT ..."
cd "$TMP_DIR" || exit 1

# --- 1️⃣ Fetch full certificate chain and split each PEM block ---
INDEX=0
while IFS= read -r line; do
  case "$line" in
    *"BEGIN CERTIFICATE"*)
      INDEX=$((INDEX+1))
      CERT_FILE="cert-${INDEX}.pem"
      echo "$line" > "$CERT_FILE"
      ;;
    *"END CERTIFICATE"*)
      echo "$line" >> "$CERT_FILE"
      ;;
    *)
      if [[ $INDEX -gt 0 ]]; then
        echo "$line" >> "$CERT_FILE"
      fi
      ;;
  esac
done < <(openssl s_client -showcerts -connect "$DOMAIN:$PORT" -servername "$DOMAIN" </dev/null 2>/dev/null)

if [ $INDEX -eq 0 ]; then
  echo "❌ No certificates extracted — maybe connection failed?"
  exit 1
fi

# --- 2️⃣ Process each certificate ---
echo "["
FIRST=1

for CERT in cert-*.pem; do
  if ! openssl x509 -in "$CERT" -noout >/dev/null 2>&1; then
    echo "⚠️  Skipping invalid cert file: $CERT"
    continue
  fi

  SUBJECT=$(openssl x509 -in "$CERT" -noout -subject | sed 's/subject=//')
  ISSUER=$(openssl x509 -in "$CERT" -noout -issuer | sed 's/issuer=//')
  NOT_BEFORE=$(openssl x509 -in "$CERT" -noout -startdate | cut -d= -f2)
  NOT_AFTER=$(openssl x509 -in "$CERT" -noout -enddate | cut -d= -f2)

  PIN=$(openssl x509 -in "$CERT" -pubkey -noout 2>/dev/null \
    | openssl pkey -pubin -outform DER 2>/dev/null \
    | openssl dgst -sha256 -binary 2>/dev/null \
    | base64 2>/dev/null || echo "ERROR")

  if [ $FIRST -eq 0 ]; then echo ","; fi
  FIRST=0

  echo "  {"
  echo "    \"file\": \"$CERT\","
  echo "    \"subject\": \"${SUBJECT//\"/\\\"}\","
  echo "    \"issuer\": \"${ISSUER//\"/\\\"}\","
  echo "    \"notBefore\": \"$NOT_BEFORE\","
  echo "    \"notAfter\": \"$NOT_AFTER\","
  echo "    \"pin_sha256\": \"$PIN\""
  echo -n "  }"
done

echo
echo "]"
echo
echo "✅ Done. PEMs saved at: $TMP_DIR"
