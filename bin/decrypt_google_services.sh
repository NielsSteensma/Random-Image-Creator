#!/bin/sh
gpg --quiet --batch --yes --decrypt --passphrase="$GOOGLE_SERVICES_PASSPHRASE" \
--output ../app/google_services.json ../app/google_services.json.gpg