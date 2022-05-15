echo -e "\n############### GIT PULL MY-PLAYGROUP ###############\n"
git -C /var/www/api/MyPlaygroup-Server pull

echo -e "\n############### MVN CLEAN INSTALL MY-PLAYGROUP ###############\n"
mvn -f /var/www/api/MyPlaygroup-Server clean install

echo -e "\n############### RESTART MY-PLAYGROUP SERVICE ###############\n"
systemctl stop playgroup
systemctl start playgroup