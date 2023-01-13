#!/bin/sh

mkdir $HOME
gpg --quiet --batch --yes --decrypt --passphrase="$GOOGLE_SERVICES_PASSPHRASE" \
--output $HOME/app/google_services.json $home/app/google_services.json.gpg