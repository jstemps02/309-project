=== VM Notes ===

This VM is hosted on the LAS infrastructure.

VMs are expected to be reasonably short-lived (one to two semesters). 
Any important data should not be stored on the VM. Please back it up 
to a location like LSS or CyBox.

If you have any questions or problems with this VM, please contact 
researchit@iastate.edu.

If this VM is for a Computer Science class, please contact
coms-ssg@iastate.edu instead of researchit@iastate.edu.


=== Starting Services ===

A few commonly used services are already installed; 
however, they do need to be enabled and started to 
use them.

Apache HTTPD:
  sudo systemctl enable httpd.service
  sudo systemctl start httpd.service

  Webroot: /var/www/html
  Config:  /etc/httpd

Apache Tomcat:
  sudo systemctl enable tomcat.service
  sudo systemctl start tomcat.service

  Webroot: /var/lib/tomcat/webapps
  Config:  /etc/tomcat

Container Engine:
  Podman has been installed with Docker CLI support.

  Once you have a container, if you want to make it start on boot:
    sudo "podman generate systemd <containername> > /etc/systemd/system/<containername>.service"
    sudo systemctl daemon-reload
    sudo systemctl enable <containername>.service
    sudo systemctl start <containername>.service

MariaDB (MySQL) Server:
  sudo /usr/local/bin/isu_mariadb_initialize

  Default MariaDB Credentials:
    username: root
    password: -UNSET-

=== A Few Useful Commands ===

We've included a few useful commands for accessing ISU
resources or enabling/disabling the GUI.

  isu_enable_gui    - Switches the VM to start and run in GUI mode.
  isu_disable_gui   - Switches the VM to start and run in CLI mode (default).

  isu_mount_modules - Mounts the Research IT software modules.
    https://researchit.las.iastate.edu/spack-based-software-modules

  isu_mount_lss     - Mounts large scale storage for copying data in and out 
                      of the system (must have an LSS account).
    https://researchit.las.iastate.edu/large-scale-storage-lss

  isu_mount_myfiles - Mounts your ISU user folder (usually known as the 'U'
                      drive).

  The isu_mount_* commands are not persistent. Whenever the VM is rebooted, 
  the command must be run again.

