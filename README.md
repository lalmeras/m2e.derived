# m2.derived

## Plugin description

This plugin adds an entry in project's contextual menu called
**Maven > Mark sub-modules as derived**. It was primarily created to
easily get rid of redundant matches in search results
(File Search/Open Resource).

It relies on m2e (Eclipse Maven integration plugin).

## How to update version

```
mvn tycho-versions:set-version -DnewVersion=0.0.X
```

You way need to update m2e version and dependencies versions :
keys ``org.eclipse.m2e.*`` in ``owsi-eclipse-m2e-plugins/fr.openwide.eclipse.plugins.m2e.derived/META-INF/MANIFEST.MF``
(check previous releases commits).

## Build and deploy

Build uses maven+tycho plugin.

To build an updated repository folder in ``owsi-eclipse-plugins-repo/target/repository``:

```
mvn clean install
```

Updated repository can be deployed using rsync:

```
# prerequisites: rclone remove eclipse-m2e-derived must be configured
# check updated files (dry-run)
rclone sync -v --dry-run ./owsi-eclipse-plugins-repo/target/repository/ eclipse-m2e-derived:eclipse-m2e-derived
# perform real update
rclone sync -v ./owsi-eclipse-plugins-repo/target/repository/ eclipse-m2e-derived:eclipse-m2e-derived
```

## About tycho configuration

To perform p2 deployment (repository update), it uses 
``tycho-p2-extras-plugin:add-to-update-site`` to download an existing
update site and build an updated folder.

Configuration is located in ``owsi-eclipse-plugins-repo/pom.xml`` and targets
to https://nexus.tools.kobalt.fr/repository/eclipse-m2e-derived/ update site.
