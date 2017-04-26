### Pre requirements

* Ansible on yor PC/Mac/Linux
* A dev server with `docker` and `docker-compose` was installed
 

### Run ansible for setup server

*Edit file `ansible/inventories/development/hosts` for your host's IP*

```sh
ansible-playbook -i inventories/development main.yml -v
```
