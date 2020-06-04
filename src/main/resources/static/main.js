Submit = () => {
    if (validation()) {
        document.getElementById("formId").submit();
    }
    validation();
}

function validation() {
    const fi = document.getElementById('file');
    if (fi.files.length > 0) {
        for (const i = 0; i <= fi.files.length - 1; i++) {
            const fsize = fi.files.item(i).size;
            const file = Math.round((fsize / 1024));
            if (file >= 2000) {
                alert(
                    "File too Big, please select a file less than 4mb");
                return false;
            }
            return true;
        }
    }
    return true;
}