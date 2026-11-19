rootProject.name = "omegaspigot"

includeBuild("build-logic")

fun setupSubproject(name: String, dir: String) {
    include(":$name")
    project(":$name").projectDir = file(dir)
}

setupSubproject("omegaspigot-server", "OmegaSpigot-Server")
setupSubproject("omegaspigot-api", "OmegaSpigot-API")
setupSubproject("paperclip", "paperclip")
