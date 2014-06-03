## Gitblit Team-Branch ACL (tbacl) Receive Hook

*REQUIRES 1.5.0*

This plugin enforces a primitive branch ACL scheme: the pushing user must be a member of a team named for the branch they want to push.

For example, if *joe* wants to push to the *master* branch he must be a member of the `master` team.

**NOTE:**
Only administrators or owners can push refs outside the branch namespace.

### Building against a Gitblit RELEASE

    mvn clean package && cp ./target/tbacl*.zip /path/to/gitblit/plugins

### Building against a Gitblit SNAPSHOT

    /path/to/dev/gitblit/ant installMaven
    /path/to/dev/tbacl/mvn clean package && cp ./target/tbacl*.zip /path/to/gitblit/plugins

