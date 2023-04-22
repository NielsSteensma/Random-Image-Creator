#!/bin/sh
gpg --quiet --batch --yes --decrypt --passphrase="$KEYSTORE_PASSPHRASE" \
--output ./keystore.jks ./keystore.jks.gpg