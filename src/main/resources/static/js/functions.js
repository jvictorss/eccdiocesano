// File: src/main/resources/static/js/functions.js

const token = localStorage.getItem('jwtToken');

function createCasalForm() {
    window.location.href = '/verbum-ecc/v1/casal/form';
}

function viewCasal() {
    window.location.href = '/verbum-ecc/v1/casal/ver-casais';
}

function viewCasaisInativos() {
    window.location.href = '/verbum-ecc/v1/casal/ver-casais-inativos';
}

function createUsuario() {
    window.location.href = '/verbum-ecc/v1/usuario/form';
}

function verUsuarios() {
    window.location.href = '/verbum-ecc/v1/usuario/view';
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
    if (response.ok) {
        return await response.json();
    } else {
        console.error('Failed to fetch data from', url);
        return {};
    }
}

async function casaisApenasComPrimeiraEtapa() {
    event.preventDefault();

    try {
        const response = await fetch(`/verbum-ecc/v1/pdf/first-step`, {
            method: 'GET',
            headers: {
                'Authorization': `Bearer ${token}`
            }
        });

        if (response.ok) {
            const blob = await response.blob();
            const url = window.URL.createObjectURL(blob);
            const a = document.createElement('a');
            a.href = url;
            a.download = 'casais-com-primeira-etapa.pdf';
            document.body.appendChild(a);
            a.click();
            a.remove();
        } else {
            alert('Falha ao emitir relatório.');
        }
    } catch (error) {
        console.error('Error emitting report:', error);
    }
}

async function casaisComSegundaEtapa() {
    event.preventDefault();

    try {
        const response = await fetch(`/verbum-ecc/v1/pdf/second-step`, {
            method: 'GET',
            headers: {
                'Authorization': `Bearer ${token}`
            }
        });

        if (response.ok) {
            const blob = await response.blob();
            const url = window.URL.createObjectURL(blob);
            const a = document.createElement('a');
            a.href = url;
            a.download = 'casais-com-segunda-etapa.pdf';
            document.body.appendChild(a);
            a.click();
            a.remove();
        } else {
            alert('Falha ao emitir relatório.');
        }
    } catch (error) {
        console.error('Error emitting report:', error);
    }
}

async function casaisComTerceiraEtapa() {
    event.preventDefault();

    try {
        const response = await fetch(`/verbum-ecc/v1/pdf/third-step`, {
            method: 'GET',
            headers: {
                'Authorization': `Bearer ${token}`
            }
        });

        if (response.ok) {
            const blob = await response.blob();
            const url = window.URL.createObjectURL(blob);
            const a = document.createElement('a');
            a.href = url;
            a.download = 'casais-com-terceira-etapa.pdf';
            document.body.appendChild(a);
            a.click();
            a.remove();
        } else {
            alert('Falha ao emitir relatório.');
        }
    } catch (error) {
        console.error('Error emitting report:', error);
    }
}

async function casaisSemMatrimonio() {
    event.preventDefault();

    try {
        const response = await fetch(`/verbum-ecc/v1/pdf/without-sacrament`, {
            method: 'GET',
            headers: {
                'Authorization': `Bearer ${token}`
            }
        });

        if (response.ok) {
            const blob = await response.blob();
            const url = window.URL.createObjectURL(blob);
            const a = document.createElement('a');
            a.href = url;
            a.download = 'casais-sem-sacramento-do-matrimonio.pdf';
            document.body.appendChild(a);
            a.click();
            a.remove();
        } else {
            alert('Falha ao emitir relatório.');
        }
    } catch (error) {
        console.error('Error emitting report:', error);
    }
}

async function casaisInativos() {
    event.preventDefault();

    try {
        const response = await fetch(`/verbum-ecc/v1/pdf/inactive-couples`, {
            method: 'GET',
            headers: {
                'Authorization': `Bearer ${token}`
            }
        });

        if (response.ok) {
            const blob = await response.blob();
            const url = window.URL.createObjectURL(blob);
            const a = document.createElement('a');
            a.href = url;
            a.download = 'casais-inativos.pdf';
            document.body.appendChild(a);
            a.click();
            a.remove();
        } else {
            alert('Falha ao emitir relatório.');
        }
    } catch (error) {
        console.error('Error emitting report:', error);
    }
}

async function casaisAtivos() {
    event.preventDefault();

    try {
        const response = await fetch(`/verbum-ecc/v1/pdf/active-couples`, {
            method: 'GET',
            headers: {
                'Authorization': `Bearer ${token}`
            }
        });

        if (response.ok) {
            const blob = await response.blob();
            const url = window.URL.createObjectURL(blob);
            const a = document.createElement('a');
            a.href = url;
            a.download = 'casais-ativos.pdf';
            document.body.appendChild(a);
            a.click();
            a.remove();
        } else {
            alert('Falha ao emitir relatório.');
        }
    } catch (error) {
        console.error('Error emitting report:', error);
    }
}

function cancel() {
    window.location.href = '/verbum-ecc/v1/home';
}

async function createUser(event) {
    event.preventDefault();

    const nome = document.getElementById('nome').value;
    const email = document.getElementById('email').value;
    const senha = document.getElementById('senha').value;
    const telefone = document.getElementById('telefone').value;
    const papel = document.getElementById('papel').value;

    const response = await fetch('/verbum-ecc/v1/usuarios/create', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            nome: nome,
            email: email,
            senha: senha,
            telefone: telefone,
            papel: papel
        })
    });

    if (response.ok) {
        alert('Usuário criado com sucesso!');
        clear();
    } else {
        alert('Erro ao criar usuário.');
    }
}

function clear() {
    document.getElementById('nome').value = '';
    document.getElementById('email').value = '';
    document.getElementById('senha').value = '';
    document.getElementById('telefone').value = '';
    document.getElementById('papel').value = '';
}

window.onload = async function() {

    const paroquiaEcc = /*[[${casal.paroquiaEcc}]]*/ '[[${casal.paroquiaEcc}]]';
    const paroquiaAtual = /*[[${casal.paroquiaAtual}]]*/ '[[${casal.paroquiaAtual}]]';

    document.getElementById('paroquiaEcc').value = paroquiaEcc;
    document.getElementById('paroquiaAtual').value = paroquiaAtual;
};

const estados = [
    "Acre", "Alagoas", "Amapá", "Amazonas", "Bahia", "Ceará", "Distrito Federal", "Espírito Santo",
    "Goiás", "Maranhão", "Mato Grosso", "Mato Grosso do Sul", "Minas Gerais", "Pará", "Paraíba",
    "Paraná", "Pernambuco", "Piauí", "Rio de Janeiro", "Rio Grande do Norte", "Rio Grande do Sul",
    "Rondônia", "Roraima", "Santa Catarina", "São Paulo", "Sergipe", "Tocantins"
];

function populateEstados() {
    const selectEstado = document.getElementById('estado');
    estados.forEach(estado => {
        const option = document.createElement('option');
        option.value = estado;
        option.textContent = estado;
        selectEstado.appendChild(option);
    });
}

async function createCasal(event) {
    event.preventDefault();

    const conjugeEle = {
        nome: document.getElementById('nomeEsposo').value,
        apelido: document.getElementById('apelidoEsposo').value,
        telefone: document.getElementById('telefoneEsposo').value,
        email: document.getElementById('emailEsposo').value,
        dataNascimento: document.getElementById('dataNascimentoEsposo').value,
        dataFalecimento: document.getElementById('dataFalecimentoEsposo').value
    };

    const conjugeEla = {
        nome: document.getElementById('nomeEsposa').value,
        apelido: document.getElementById('apelidoEsposa').value,
        telefone: document.getElementById('telefoneEsposa').value,
        email: document.getElementById('emailEsposa').value,
        dataNascimento: document.getElementById('dataNascimentoEsposa').value,
        dataFalecimento: document.getElementById('dataFalecimentoEsposa').value
    };

    const casalData = {
        ele: conjugeEle,
        ela: conjugeEla,
        dataCasamentoReligioso: document.getElementById('dataCasamentoReligioso').value,
        dataCasamentoCivil: document.getElementById('dataCasamentoCivil').value,
        endereco: document.getElementById('endereco').value,
        bairro: document.getElementById('bairro').value,
        cidade: document.getElementById('cidade').value,
        estado: document.getElementById('estado').value,
        paroquiaEcc: document.getElementById('paroquiaEcc').value,
        paroquiaAtual: document.getElementById('paroquiaAtual').value,
        dataPrimeiraEtapa: document.getElementById('dataPrimeiraEtapa').value,
        dataSegundaEtapa: document.getElementById('dataSegundaEtapa').value,
        dataTerceiraEtapa: document.getElementById('dataTerceiraEtapa').value,
        isActive: document.getElementById('isActive').checked
    };

    try {
        const response = await fetch('/verbum-ecc/v1/casal/create', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(casalData)
        });

        if (response.ok) {
            alert('Casal criado com sucesso!');
        } else {
            console.error('Falha ao criar casal');
        }
    } catch (error) {
        console.error('Erro ao criar casal:', error);
    }
}

function cancel() {
    window.location.href = '/verbum-ecc/v1/home';
}

function clear() {
    document.getElementById('nome').value = '';
    document.getElementById('cidade').value = '';
    document.getElementById('estado').value = '';
    document.getElementById('diocese').value = '';
    document.getElementById('setor').value = '';
    document.getElementById('tenantId').value = '';
    document.getElementById('isActive').checked = false;
}

document.addEventListener('DOMContentLoaded', populateEstados);

window.onload = async function() {
    populateEstados();
};