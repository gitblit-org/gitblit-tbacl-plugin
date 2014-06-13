## Gitblit Team-Branch ACL (tbacl) Receive Hook

*REQUIRES 1.5.0*

This plugin enforces a primitive branch ACL scheme: the pushing user must be a member of a team named for the branch they want to push.

For example, if *joe* wants to push to the *master* branch he must be a member of the `master` team.

**NOTE:**
Only administrators or owners can push refs outside the branch namespace.

### Installation

This plugin is referenced in the Gitblit Plugin Registry and you may install it using SSH with an administrator account.

    ssh host plugin refresh
    ssh host plugin install tbacl
    ssh host plugin ls

Alternatively, you can download the zip and manually copy it to your `${baseFolder}/plugins` directory.

### Setup

There is no required setup but there are some optional settings you may want to configure.

#### tbacl.applyToPersonalRepos

*tbacl.applyToPersonalRepos* = true will apply the branch<->team requirement to personal repositories.

By default, this setting is *false* so the tbacl plugin ignore personal repositories.

### Building against a Gitblit RELEASE

    ant && cp ./build/target/tbacl*.zip /path/to/gitblit/plugins

### Building against a Gitblit SNAPSHOT

    /path/to/dev/gitblit/ant installMoxie
    /path/to/dev/tbacl/ant && cp ./build/target/tbacl*.zip /path/to/gitblit/plugins

