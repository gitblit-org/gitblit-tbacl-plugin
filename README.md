## Gitblit Team-Branch ACLs

*REQUIRES 1.5.0*

This plugin requires enforces a relationship between teams and target branches; i.e. the user pushing commits must be a team member to push to the team branches.  Pushes outside the team branches sandbox are rejected unless the pushing user is an owner or administrator.

For example, if *joe* wants to push to the *master* branch he must be a member of the `master` team.

Or if team namespaces are enabled, *joe* can push to the *qa/master* branch if he is a member of the `qa` team.

**NOTE:**
Only administrators or owners can push refs outside the branch namespace (e.g. tags).

### Installation

This plugin is referenced in the Gitblit Plugin Registry and you may install it using SSH with an administrator account.

    ssh host plugin refresh
    ssh host plugin install tbacl
    ssh host plugin ls

Alternatively, you can download the zip from [here](http://plugins.gitblit.com) manually copy it to your `${baseFolder}/plugins` directory.

### Setup

There is no required setup but there are some optional settings you may want to configure.

#### tbacl.useTeamNamespaces

DEFAULT: **false**, *strict team<->branch mapping*

*tbacl.useTeamNamespaces* = true will require that non-admins and non-owners push to a team branch namespace.  This is a little more flexible than the default strict teamname<->branchname requirement.

For example, *qa* team members are allowed to push to:

- refs/heads/qa/master
- refs/heads/qa/develop

But **not** to:

- refs/heads/qa

And *coders* team members are allowed to push to:

- refs/heads/coders/master
- refs/heads/coders/develop

But **not** to:

- refs/heads/coders

#### tbacl.applyToPersonalRepos

DEFAULT: **false**, *personal repositories are skipped*

*tbacl.applyToPersonalRepos* = true will apply the branch<->team requirement to personal repositories.

### Building against a Gitblit RELEASE

    ant && cp ./build/target/tbacl*.zip /path/to/gitblit/plugins

### Building against a Gitblit SNAPSHOT

    /path/to/dev/gitblit/ant installMoxie
    /path/to/dev/tbacl/ant && cp ./build/target/tbacl*.zip /path/to/gitblit/plugins

