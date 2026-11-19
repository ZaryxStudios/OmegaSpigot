# OmegaSpigot
OmegaSpigot is an open-source fork of [PandaSpigot](https://github.com/hpfxd/PandaSpigot) for Minecraft 1.8.8,
designed specifically for compatibility with [OmegaHCF](https://github.com/ZaryxStudios/OmegaHCF).

OmegaSpigot is a fork of PandaSpigot, which itself is a fork of [Paper](https://github.com/PaperMC/Paper-archive)
(Paper for 1.8.8, focused on improved performance and stability). All credit for the original work goes to the
PandaSpigot and Paper authors. OmegaSpigot is distributed under the same license as its upstreams (see [LICENSE.md](./LICENSE.md)).

## Highlights
- **Backported API enhancements from newer versions**
    - Native Adventure API
    - ServerTickStartEvent & ServerTickEndEvent
    - PlayerChunkLoadEvent & PlayerChunkUnloadEvent
    - PlayerHandshakeEvent
    - EntityMoveEvent

- **Greatly improved network performance**
    - **Updating to Netty 4.1** offers the ability to use newer Java versions with epoll on Linux.
    - **Improved flush handling** to massively improve entity tracker performance.
    - **Support for Unix domain sockets** to avoid the overhead of TCP when using a proxy on the same machine.

- **More configuration options**, such as:
    - Customizable knockback
    - World and player data saving

See a full list of patches [here](./patches/).

## Using
Build the server JAR yourself with the `omega` script (see below). The resulting `paperclip.jar` is the server.

For upstream PandaSpigot support, see its [Discord](https://discord.gg/m6vCCX6Hvr).

## API 
See our API patches [here](./patches/api/). OmegaSpigot-API is not published to a public Maven repository; build it from source and depend on it as a local artifact.
<details>
<summary>Maven</summary>

```xml
<dependencies>
    <dependency>
        <groupId>dev.zaryxstudios.escanergang.omegaspigot</groupId>
        <artifactId>omegaspigot-api</artifactId>
        <version>1.8.8-R0.1-SNAPSHOT</version>
        <scope>provided</scope>
    </dependency>
</dependencies>
```
</details>

<details>
<summary>Gradle (kts)</summary>

```kotlin
dependencies {
    compileOnly("dev.zaryxstudios.escanergang.omegaspigot:omegaspigot-api:1.8.8-R0.1-SNAPSHOT")
}
```
</details>

## Requirements
To work with OmegaSpigot, you will need:
- JDK 17 or higher
- Git
- Bash

🧩 Although JDK 17 is required for building, the compiled JAR remains fully compatible with Java 8.

### Omega Bash build script
The `omega` Bash script is the entry point for setup, patching, and builds.
Run it from a Bash-compatible shell on your platform. Run `./omega` without
arguments to print its command list.

Normal commands:

| Command | Description |
| --- | --- |
| `rb`, `rebuild` | Rebuild patches, can be called from anywhere. |
| `setup` | Remap, decompile, and patch Minecraft. Can be run from anywhere. |
| `p`, `patch` | Apply all patches to the project without building it. Can be run from anywhere. |
| `j`, `jar` | Build the project, `paperclip.jar` will be output. Can be run from anywhere. |
| `c`, `clean` | Removes build files under `OmegaSpigot-API`, `OmegaSpigot-Server`. |
| `dc`, `distclean` | Removes all generated files, `OmegaSpigot-API`, `OmegaSpigot-Server`, `base`, and `work`. |
| `con`, `continue` | Shortcut command for running `git am --continue` or `git rebase --continue`. |

Commands that require `. ./omega install` first:

| Command | Description |
| --- | --- |
| `r`, `root` | Change directory to the root of the project. |
| `a`, `api` | Move to the `OmegaSpigot-API` directory. |
| `s`, `server` | Move to the `OmegaSpigot-Server` directory. |
| `e`, `edit` | Use to edit a specific patch, give it the argument `server` or `api` respectively to edit the correct project. Use the argument `continue` after the changes have been made to finish and rebuild patches. Can be called from anywhere. |
| `install` | Add an alias to `$RCPATH` to allow full functionality of this script. |

Install the default `omega` alias with:

```bash
. ./omega install
```

You can also provide a custom alias name:

```bash
. ./omega install example
```

`OmegaSpigot-API` and `OmegaSpigot-Server` are generated work trees created by
the build scripts. The patch creation process follows Paper's contributing guide,
which is linked below; `./omega rb` is the OmegaSpigot command that saves those
work tree commits back into `patches/api` and `patches/server`.

### Patch workflow
OmegaSpigot uses a patch-based workflow. Changes are committed once inside the
generated work tree, then committed again from the repository root after the
patch files are rebuilt.

Use this order:

1. Make your changes in `OmegaSpigot-API` or `OmegaSpigot-Server`.
2. Commit those changes inside that generated work tree.
3. Return to the repository root and run `./omega rb`.
4. Review the regenerated files in `patches/api` or `patches/server`.
5. Commit the regenerated patch files from the repository root.

Only push commits from the repository root. Do not push or pull from
`OmegaSpigot-API` or `OmegaSpigot-Server`; their commits are only used to create
patch files. Commit work tree changes before rebuilding or reapplying patches,
because those commands can reset the generated work trees.

## Contributing
You can mostly follow [Paper's contributing guide](https://github.com/PaperMC/Paper-archive/blob/ver/1.16.5/CONTRIBUTING.md), just remember:
- Multi-line changes start with `// PandaSpigot start` and end with `// PandaSpigot end`
- If the change isn't obvious, add a small explanation like this: `// PandaSpigot start - reason`
- One-line changes should have `// PandaSpigot` at the end of the line.
- Follow Java code style (aka. Oracle style), with some exceptions:
  - If you are modifying upstream files, keep your diff size minimal. Going over 80 characters per line is fine to make this happen.
  - When in doubt or the code around your change is in a clearly different style, use the same style as the surrounding code.

When contributing, please think about the side effects of any changes you write.
Plugin compatibility is important, and we wish to minimize any breakage.

Please do not open pull requests for features that you cannot justify the existence of,
and the added maintenance costs of that come along with them. If you are thinking of
adding a feature that may be controversial, please open an issue first!
