customFile.onchange = evt => {
    const [file] = customFile.files
    if (file) {
        prevImg.src = URL.createObjectURL(file)
    }
}