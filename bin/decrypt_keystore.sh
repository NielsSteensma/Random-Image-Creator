#!/bin/sh
gpg --quiet --batch --yes --decrypt --passphrase="$KEYSTORE_PASSPHRASE" \
--output ./keystore.jks ./app/keystore.jks.gpg