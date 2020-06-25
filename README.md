# grails-ncar-extensions
Grails utilities and extensions plugin

## Development

Use `./grailsShell` to get a reasonable Grails shell (REPL).
After exiting, you will still have to `^C`.

## Deployment

Version number is set in `application.gradle` to avoid messing around in `build.gradle`.

Use `./grailsw install` to install to your local Maven repo (e.g. `~/.m2/`).
Use `./grailsw publish-plugin` to publish to the EOL internal Maven remote repo,
defined by `internalMavenPublishUrl` in `build.gradle` (you can override this
in `local.gradle`).
