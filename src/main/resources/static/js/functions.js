// File: src/main/resources/static/js/functions.js

const token = localStorage.getItem('jwtToken');

function createDiocese() {
    window.location.href = '/verbum-ecc/v1/diocese/form';
}

function viewDioceses() {
    window.location.href = '/verbum-ecc/v1/diocese/view';
}

function updateDiocese(id) {
    window.location.href = `/verbum-ecc/v1/diocese/${id}`;
}

function updateSetor(id) {
    window.location.href = `/verbum-ecc/v1/setorial/${id}`;
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

function createCasalForm() {
    window.location.href = '/verbum-ecc/v1/casal/form';
}

function viewCasal() {
    window.location.href = '/verbum-ecc/v1/casal/ver-casais';
}

function viewCasaisInativos() {
    window.location.href = '/verbum-ecc/v1/casal/ver-casais-inativos';
}

function casaisParaSegundaEtapa() {
    window.location.href = '/verbum-ecc/v1/relatorios/casais-para-segunda-etapa';
}

function casaisParaTerceiraEtapa() {
    window.location.href = '/verbum-ecc/v1/relatorios/casais-para-terceira-etapa';
}

function qtdCasaisSemMatrimonio() {
    window.location.href = '/verbum-ecc/v1/relatorios/casais-sem-sacramento';
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

async function fetchDioceses() {
    try {
        const response = await fetch('/verbum-ecc/v1/diocese/all/isActive=true');
        if (response.ok) {
            const dioceses = await response.json();
            const tableBody = document.getElementById('dioceseTableBody');
            dioceses.forEach(diocese => {
                const row = document.createElement('tr');
                row.innerHTML = `
                    <td>${diocese.nome}</td>
                    <td>${diocese.cidade}</td>
                    <td>${diocese.estado}</td>
                    <td>
                        <button onclick="updateDiocese('${diocese.id}')">Editar</button>
                        <button onclick="deleteDiocese('${diocese.id}')">Excluir</button>
                    </td>
                `;
                tableBody.appendChild(row);
            });
        } else {
            console.error('Failed to fetch dioceses');
        }
    } catch (error) {
        console.error('Error fetching dioceses:', error);
    }
}

async function deleteDiocese(id) {
    if (confirm('Tem certeza que deseja excluir esta diocese?')) {
        try {
            const response = await fetch(`/verbum-ecc/v1/diocese/${id}`, {
                method: 'DELETE'
            });
            if (response.ok) {
                alert('Diocese excluída com sucesso!');
                location.reload();
            } else {
                alert('Falha ao excluir diocese. Existem setores cadastrados nela.');
            }
        } catch (error) {
            console.error('Error deleting diocese:', error);
        }
    }
}

async function emitirRelatorioParaSegundaEtapa(event) {
    event.preventDefault();

    const paroquiaId = document.getElementById('paroquia').value;

    if (!paroquiaId) {
        alert('Por favor, selecione uma paróquia.');
        return;
    }

    try {
        const response = await fetch(`http://localhost:8080/verbum-ecc/v1/pdf/first-step/${paroquiaId}`, {
            method: 'GET',
            // headers: {
            //     'Authorization': `Bearer ${token}`
            // }
        });

        if (response.ok) {
            const blob = await response.blob();
            const url = window.URL.createObjectURL(blob);
            const a = document.createElement('a');
            a.href = url;
            a.download = 'casais-para-segunda-etapa.pdf';
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

async function emitirRelatorioParaTerceiraEtapa(event) {
    event.preventDefault();

    const paroquiaId = document.getElementById('paroquia').value;

    if (!paroquiaId) {
        alert('Por favor, selecione uma paróquia.');
        return;
    }

    try {
        const response = await fetch(`http://localhost:8080/verbum-ecc/v1/pdf/second-step/${paroquiaId}`, {
            method: 'GET',
            // headers: {
            //     'Authorization': `Bearer ${token}`
            // }
        });

        if (response.ok) {
            const blob = await response.blob();
            const url = window.URL.createObjectURL(blob);
            const a = document.createElement('a');
            a.href = url;
            a.download = 'casais-para-terceira-etapa.pdf';
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

async function casaisSemMatrimonio(event) {
    event.preventDefault();

    const paroquiaId = document.getElementById('paroquia').value;

    if (!paroquiaId) {
        alert('Por favor, selecione uma paróquia.');
        return;
    }

    try {
        const response = await fetch(`/verbum-ecc/v1/pdf/without-sacrament/${paroquiaId}`, {
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

function cancel() {
    window.location.href = '/verbum-ecc/v1/home';
}

document.addEventListener('DOMContentLoaded', fetchDioceses);

async function fetchSetores() {
    try {
        const response = await fetch('/verbum-ecc/v1/setorial/all/isActive=true', {
            method: 'GET'
        });

        if (response.ok) {
            const setores = await response.json();
            const select = document.getElementById('setor');
            setores.forEach(setor => {
                const option = document.createElement('option');
                option.value = setor.id;
                option.textContent = setor.nome;
                select.appendChild(option);
            });
        } else {
            console.error('Failed to fetch setores');
        }
    } catch (error) {
        console.error('Error fetching setores:', error);
    }
}

async function fetchParoquias() {
    try {
        const response = await fetch('http://localhost:8080/verbum-ecc/v1/paroquia/all/isActive=true', {
            method: 'GET'
        });

        if (response.ok) {
            const paroquias = await response.json();
            const select = document.getElementById('paroquia');
            paroquias.forEach(paroquia => {
                const option = document.createElement('option');
                option.value = paroquia.id;
                option.textContent = paroquia.nome;
                select.appendChild(option);
            });
        } else {
            console.error('Failed to fetch paróquias');
        }
    } catch (error) {
        console.error('Error fetching paróquias:', error);
    }
}

async function createUser(event) {
    event.preventDefault();

    const nome = document.getElementById('nome').value;
    const email = document.getElementById('email').value;
    const senha = document.getElementById('senha').value;
    const telefone = document.getElementById('telefone').value;
    const papel = document.getElementById('papel').value;
    const idDiocese = document.getElementById('diocese').value;
    const idSetor = document.getElementById('setor').value;
    const idParoquia = document.getElementById('paroquia').value;

    const response = await fetch('http://localhost:8080/verbum-ecc/v1/usuarios/create', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            nome: nome,
            email: email,
            senha: senha,
            telefone: telefone,
            papel: papel,
            idDiocese: idDiocese,
            idSetor: idSetor,
            idParoquia: idParoquia
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
    document.getElementById('diocese').value = '';
    document.getElementById('setor').value = '';
    document.getElementById('paroquia').value = '';
}

window.onload = async function() {

    const paroquiaEccId = /*[[${casal.paroquiaEcc}]]*/ '[[${casal.paroquiaEcc}]]';
    const paroquiaAtualId = /*[[${casal.paroquiaAtual}]]*/ '[[${casal.paroquiaAtual}]]';
    const setorId = /*[[${casal.idSetor}]]*/ '[[${casal.idSetor}]]';
    const dioceseId = /*[[${casal.idDiocese}]]*/ '[[${casal.idDiocese}]]';

    const paroquiaEccName = await fetchParoquiaName(paroquiaEccId);
    const paroquiaAtualName = await fetchParoquiaName(paroquiaAtualId);
    const setorName = await fetchSetorName(setorId);
    const dioceseName = await fetchDioceseName(dioceseId);

    document.getElementById('paroquiaEcc').value = paroquiaEccName;
    document.getElementById('paroquiaAtual').value = paroquiaAtualName;
    document.getElementById('idSetor').value = setorName;
    document.getElementById('idDiocese').value = dioceseName;
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
        cpf: document.getElementById('cpfEsposo').value,
        dataNascimento: document.getElementById('dataNascimentoEsposo').value
    };

    const conjugeEla = {
        nome: document.getElementById('nomeEsposa').value,
        apelido: document.getElementById('apelidoEsposa').value,
        telefone: document.getElementById('telefoneEsposa').value,
        email: document.getElementById('emailEsposa').value,
        cpf: document.getElementById('cpfEsposa').value,
        dataNascimento: document.getElementById('dataNascimentoEsposa').value
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
        idSetor: document.getElementById('setor').value,
        idDiocese: document.getElementById('diocese').value,
        dataPrimeiraEtapa: document.getElementById('dataPrimeiraEtapa').value,
        dataSegundaEtapa: document.getElementById('dataSegundaEtapa').value,
        dataTerceiraEtapa: document.getElementById('dataTerceiraEtapa').value,
        isActive: document.getElementById('isActive').checked
    };

    try {
        const response = await fetch('http://localhost:8080/verbum-ecc/v1/casal/create', {
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

document.addEventListener('DOMContentLoaded', fetchDioceses);
document.addEventListener('DOMContentLoaded', fetchSetores);
document.addEventListener('DOMContentLoaded', fetchParoquias);
document.addEventListener('DOMContentLoaded', populateEstados);

window.onload = async function() {
    // await fetchDioceses();
    // await fetchSetores();
    // await fetchParoquias();
    populateEstados();
};