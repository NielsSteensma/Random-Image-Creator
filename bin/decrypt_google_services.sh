#!/bin/sh
gpg --quiet --batch --yes --decrypt --passphrase="$GOOGLE_SERVICES_PASSPHRASE" \
--output ./app/google-services.json ./app/google-services.json.gpg