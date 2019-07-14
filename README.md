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

## Build and deploy

Build uses maven+tycho plugin.

To build an updated repository folder in ``owsi-eclipse-plugins-repo/target/repository``:

```
mvn clean install
```

Updated repository can be deployed using rsync:

```
# check updated files (dry-run)
rsync -n -c -av ./owsi-eclipse-plugins-repo/target/repository/ dl.likide.org:/data/services/dl/documentroot/eclipse/repositories/m2.derived/
# perform real update
rsync -c -av ./owsi-eclipse-plugins-repo/target/repository/ dl.likide.org:/data/services/dl/documentroot/eclipse/repositories/m2.derived/
```

## About tycho configuration

To perform p2 deployment (repository update), it uses 
``tycho-p2-extras-plugin:add-to-update-site`` to download an existing
update site and build an updated folder.

Configuration is located in ``owsi-eclipse-plugins-repo/pom.xml`` and targets
to http://dl.likide.org/eclipse/repositories/m2.derived/ update site.
