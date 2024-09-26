
const token = localStorage.getItem('jwtToken')
    function createDiocese() {
    window.location.href = '/verbum-ecc/v1/diocese/form';
}

    function viewDioceses() {
    window.location.href = '/verbum-ecc/v1/diocese/view';
}

    function createSetor() {
    window.location.href = '/verbum-ecc/v1/setor/form';
}

    function viewSetores() {
    window.location.href = '/verbum-ecc/v1/setor/view';
}

    function createParoquia() {
    window.location.href = '/verbum-ecc/v1/paroquia/form';
}

    function viewParoquias() {
    window.location.href = '/verbum-ecc/v1/paroquia/view';
}

    function createCasal() {
    window.location.href = '/verbum-ecc/v1/casal/form';
}

    function viewCasal() {
    window.location.href = '/verbum-ecc/v1/casal/view';
}

    function createUsuario() {
    window.location.href = '/verbum-ecc/v1/usuario/form'
}

    function verUsuarios() {
    window.location.href = '/verbum-ecc/v1/usuario/view'
}

    function sair() {
    window.location.href = '/verbum-ecc/v1/login';
    localStorage.clear();
}

    async function fetchData(url) {
    const token = localStorage.getItem('jwtToken');
    const response = await fetch(url, {
    headers: {
    'Authorization': `Bearer ${token}`
}
});
    // ... (process response)
}