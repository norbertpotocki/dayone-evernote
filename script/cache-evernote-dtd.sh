#!/bin/bash

mkdir dtd
cd dtd

echo "Downloading Evernote's DTD"
curl http://xml.evernote.com/pub/enml2.dtd > enml2.dtd

echo "Downloading Evernote's DTD dependencies 1/3"
#curl http://www.w3.org/TR/xhtml1/DTD/xhtml-lat1.ent > xhtml-lat1.ent

echo "Downloading Evernote's DTD dependencies 2/3"
#curl http://www.w3.org/TR/xhtml1/DTD/xhtml-symbol.ent > xhtml-symbol.ent

echo "Downloading Evernote's DTD dependencies 3/3"
#curl http://www.w3.org/TR/xhtml1/DTD/xhtml-special.ent > xhtml-special.ent

echo "Altering DTD to use cached dependencies"

sed -e 's/http:\/\/www\.w3\.org\/TR\/xhtml1\/DTD\/xhtml-lat1\.ent/xhtml-lat1.ent/' enml2.dtd > enml2.dtd2
sed -e 's/http:\/\/www\.w3\.org\/TR\/xhtml1\/DTD\/xhtml-symbol\.ent/xhtml-symbol.ent/' enml2.dtd2 > enml2.dtd
sed -e 's/http:\/\/www\.w3\.org\/TR\/xhtml1\/DTD\/xhtml-special\.ent/xhtml-special.ent/' enml2.dtd > enml2.dtd2

mv enml2.dtd2 enml2.dtd

cd ../