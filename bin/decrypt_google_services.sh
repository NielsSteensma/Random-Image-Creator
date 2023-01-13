#!/bin/sh
gpg --quiet --batch --yes --decrypt --passphrase="$GOOGLE_SERVICES_PASSPHRASE" \
--output $HOME/app/google_services.json $HOME/app/google_services.json.gpg