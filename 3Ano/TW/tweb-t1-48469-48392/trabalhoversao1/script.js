/* 
********** header ************
*/
var ul = document.querySelector('nav ul');
var menuBtn = document.querySelector('.menu-btn i');

function menuShow() {
  if (ul.classList.contains('open')) {
    ul.classList.remove('open');
  } else {
    ul.classList.add('open');
  }
}
/* 
********** end header ************
*/

/* 
********** farmacias ************
*/

let farmacia = {

  farmacias: []
}

let detalhe = {
  pagina: 1,
  detalhes: []
}

let currentPage = 1;


function getFarmacias(currentPage) {
  var xhttp = new XMLHttpRequest();
  xhttp.onreadystatechange = function () {
    if (this.readyState == 4 && this.status == 200) {
      const obj = JSON.parse(xhttp.responseText);
      farmacia.farmacias = obj.farmacias; 
      farmacia.pagina = currentPage; 

      listarFarmacias();
      console.log(obj.farmacias);
      console.log(farmacia.farmacias);

      let target = document.getElementById("pagina");
      target.innerHTML = "Página: " + farmacia.pagina;
    }
  };

  xhttp.open("POST", "https://magno.di.uevora.pt/tweb/t1/farmacia/list", true);
  xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
  xhttp.send("page=" + currentPage);
}


// Função para carregar a próxima página
function nextPage() {
  if (currentPage < 9) {
    currentPage++;
    getFarmacias(currentPage);
  }

}

// Função para carregar a página anterior
function prePage() {
  if (currentPage > 1) {
    currentPage--;
    getFarmacias(currentPage);
  }
}

//chamado no getfarmacias
function listarFarmacias() {
  let target = document.getElementById("farmacia-lista");
  target.innerHTML = "";
  for (let farmaciaItem of farmacia.farmacias) {
    var farmaciaInfo = document.createElement("p");
    farmaciaInfo.innerHTML = `ID: ${farmaciaItem.entity_id} Nome: ${farmaciaItem.name} Local: ${farmaciaItem.postal_code_locality} <button onclick="getDetalhesFarmacia(${farmaciaItem.entity_id})">Ver Detalhes</button>`;
    target.appendChild(farmaciaInfo);
  }
}

function allFarmacias() {
  var xhttp = new XMLHttpRequest();
  xhttp.onreadystatechange = function () {
    if (this.readyState == 4 && this.status == 200) {
      const obj = JSON.parse(xhttp.responseText);
      farmacia.farmacias = obj.farmacias; 
      console.log(farmacia.farmacias);
    }
  };
  xhttp.open("GET", `https://magno.di.uevora.pt/tweb/t1/farmacia/list`, true);
  xhttp.send();
}

function filterFarmaciasByLocalidade() {

  allFarmacias();
  let selectedLocalidade = document.getElementById("localidadeSelect").value.toLowerCase();
  let target = document.getElementById("farmacia-lista");
  target.innerHTML = "";

  for (let farmaciaItem of farmacia.farmacias) {
    if (selectedLocalidade === '' || farmaciaItem.postal_code_locality.toLowerCase() === selectedLocalidade) {
      var farmaciaInfo = document.createElement("p");
      farmaciaInfo.innerHTML = `ID: ${farmaciaItem.entity_id} Nome: ${farmaciaItem.name} Local: ${farmaciaItem.postal_code_locality} <button onclick="getDetalhesFarmacia(${farmaciaItem.entity_id})">Ver Detalhes</button>`;
      target.appendChild(farmaciaInfo);
    }
  }
}


function filterFarmaciasByName() {

  allFarmacias();

  let selectedName = document.getElementById("name").value.toLowerCase();
  let target = document.getElementById("farmacia-lista");
  target.innerHTML = "";

  for (let farmaciaItem of farmacia.farmacias) {
    if (selectedName === '' || farmaciaItem.name.toLowerCase() === selectedName) {
      var farmaciaInfo = document.createElement("p");
      farmaciaInfo.innerHTML = `ID: ${farmaciaItem.entity_id} Nome: ${farmaciaItem.name} Local: ${farmaciaItem.postal_code_locality} <button onclick="getDetalhesFarmacia(${farmaciaItem.entity_id})">Ver Detalhes</button>`;
      target.appendChild(farmaciaInfo);
    }
  }
}

function searchVaccine() {
  const selectedVaccine = document.querySelector('input[name="vaccine"]:checked').value;

  const xhttp = new XMLHttpRequest();
  xhttp.onreadystatechange = function () {
    if (this.readyState == 4 && this.status == 200) {
      const obj = JSON.parse(xhttp.responseText);

      farmacia.farmacias = obj.farmacias;
      farmacia.pagina = 1;
      listarFarmacias();
      console.log(obj.farmacias);
    }
  };

  xhttp.open("POST", "https://magno.di.uevora.pt/tweb/t1/farmacia/searchvaccine", true);
  xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");

  const params = "vaccine=" + selectedVaccine;
  xhttp.send(params);
}

function getDetalhesFarmacia(farmaciaId) {
  const detalhesEndpoint = "https://magno.di.uevora.pt/tweb/t1/farmacia/get";

  const xhttpDetalhes = new XMLHttpRequest();
  xhttpDetalhes.onreadystatechange = function () {
    if (this.readyState == 4 && this.status == 200) {
      const detalhesFarmacia = JSON.parse(xhttpDetalhes.responseText);

      console.log("Detalhes da Farmácia:", detalhesFarmacia);

      const detalhesFarmaciaDiv = document.getElementById("detalhes-farmacia");
      detalhesFarmaciaDiv.innerHTML = `
        <h2>${detalhesFarmacia.farmacia.name}</h2>
        <p>Serviços: ${detalhesFarmacia.farmacia.services}</p>
        <p>Email: ${detalhesFarmacia.farmacia.email}</p>
        <p>Telefone: ${detalhesFarmacia.farmacia.telephone}</p>
        <p>Morada: ${detalhesFarmacia.farmacia.street_name}, ${detalhesFarmacia.farmacia.postal_code_region}</p>

        <button onclick="agendarFarmacia(${farmaciaId}, '${detalhesFarmacia.farmacia.name}')">Agendar Vacina</button>
      `;
    }


  };

  const detalhesUrl = `${detalhesEndpoint}/${farmaciaId}`;
  xhttpDetalhes.open("GET", detalhesUrl, true);
  xhttpDetalhes.send();
}

/* 
********** end farmacias ************
*/


/* 
********** agendamentos ************
*/

function listarAgendamentos() {
  let user_id = document.getElementById("user").value;
  console.log(user_id);
  var xhttp = new XMLHttpRequest();
  xhttp.onreadystatechange = function () {
    if (this.readyState == 4 && this.status == 200) {
      const resposta = JSON.parse(xhttp.responseText);
      console.log(resposta);
      if (resposta.status === "ok" && resposta.schedule_list) {
        const agendamentos = resposta.schedule_list;

        exibirAgendamentos(agendamentos);
      } else {
        console.error("Erro ao obter lista de agendamentos:", resposta.schedule_msg);
      }
    }
  };

  xhttp.open("POST", "https://magno.di.uevora.pt/tweb/t1/schedule/list", true);
  xhttp.setRequestHeader("Content-Type","application/x-www-form-urlencoded");  
  xhttp.send("user_id=" + encodeURIComponent(user_id));   
}

// Função para exibir os agendamentos na div "lista-agendamentos"
function exibirAgendamentos(agendamentos) {
  const listaAgendamentosDiv = document.getElementById("lista-agendamentos");

  listaAgendamentosDiv.innerHTML = "";

  if (agendamentos.length > 0) {
    const ul = document.createElement("ul");
    agendamentos.forEach((agendamento) => {
      const li = document.createElement("li");
      li.textContent = `User: ${agendamento[0]}, Farmácia: ${agendamento[1]}, Tipo de Vacina: ${agendamento[2]}, Data: ${agendamento[3]}, id_agendamento: ${agendamento[4]}`;
      ul.appendChild(li);
    });
    listaAgendamentosDiv.appendChild(ul);
  } else {
    listaAgendamentosDiv.textContent = "Nenhum agendamento encontrado.";
  }
}

// Função para agendar uma farmácia
function agendarFarmacia(farmaciaId, farmaciaName) {
  const formularioAgendamento = criarFormularioAgendamento(farmaciaId, farmaciaName);
  exibirFormularioAgendamento(formularioAgendamento);
}

// Função para criar o formulário de agendamento
function criarFormularioAgendamento(farmaciaId, farmaciaName) {
  const formulario = document.createElement("form");

  formulario.appendChild(criarCampo("ID do Usuário", "text"));
  formulario.appendChild(criarCampo("Data da Vacina", "date"));

  const enviarBtn = document.createElement("button");
  enviarBtn.textContent = "Agendar";
  enviarBtn.onclick = function () {
    const agendamentoData = obterDadosFormulario(farmaciaId, farmaciaName, formulario);
    enviarSolicitacaoAgendamento(agendamentoData);
  };

  formulario.appendChild(enviarBtn);
  return formulario;
}

// Função para criar um campo de formulário
function criarCampo(placeholder, type) {
  const input = document.createElement("input");
  input.type = type;
  input.placeholder = placeholder;
  return input;
}

// Função para obter os dados do formulário
function obterDadosFormulario(farmaciaId, farmaciaName, formulario) {
  const userId = formulario.elements[0].value;
  const tipoVacina = formulario.elements[1].value;
  const dataVacina = formulario.elements[2].value;

  return {
    farmacia_id: farmaciaId,
    user_id: userId,
    vaccine_type: tipoVacina,
    vaccine_date: dataVacina,
    farmacia_name: farmaciaName,
  };
}

// Função para exibir o formulário de agendamento
function exibirFormularioAgendamento(formulario) {
  const detalhesFarmaciaDiv = document.getElementById("detalhes-farmacia");
  detalhesFarmaciaDiv.innerHTML = "";
  detalhesFarmaciaDiv.appendChild(formulario);
}
function enviarSolicitacaoAgendamento(agendamentoData) {
  const xhttp = new XMLHttpRequest();

  xhttp.onreadystatechange = function () {
    if (this.readyState == 4) {
      if (this.status >= 200 && this.status < 300) {
        const resposta = JSON.parse(xhttp.responseText);
        console.log("Resposta do servidor:", resposta);

        if (resposta.status === "ok") {
          exibirMensagemAgendamento(resposta);
          listarAgendamentos();
        } else {
          console.error("Erro ao agendar:", resposta.schedule_msg);
        }
      } else {
        console.error("Erro ao agendar:", this.statusText);
      }
    }
  };

  const url = "https://magno.di.uevora.pt/tweb/t1/schedule/add";
  const params = `farmacia_id=${agendamentoData.farmacia_id}&user_id=${agendamentoData.user_id}&vaccine_type=${agendamentoData.vaccine_type}&vaccine_date=${agendamentoData.vaccine_date}&farmacia_name=${agendamentoData.farmacia_name}`;

  xhttp.open("POST", url, true);
  xhttp.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
  xhttp.send(params);
}

function exibirMensagemAgendamento(resposta) {
  const listaAgendamentosDiv = document.getElementById("lista-agendamentos");
  listaAgendamentosDiv.innerHTML = `<p>${resposta.schedule_msg}</p>`;
}

function tirarFarmacia() {
  const xhttpRemover = new XMLHttpRequest();
  let idAgendamento = document.getElementById("remove").value;
  xhttpRemover.onreadystatechange = function () {
    if (this.readyState == 4) {
      if (this.status >= 200 && this.status < 300) {
        const resposta = JSON.parse(xhttpRemover.responseText);
        console.log("Resposta do servidor ao remover farmácia:", resposta);

        if (resposta.status === "ok") {
          listarAgendamentos();
        } else {
          console.error("Erro ao remover farmácia:", resposta.schedule_msg);
        }
      } else {
        console.error("Erro ao remover farmácia:", this.statusText);
      }
    }
  };

  const urlRemover = "https://magno.di.uevora.pt/tweb/t1/schedule/remove";
  const paramsRemover = `id_agendamento=${idAgendamento}`;

  xhttpRemover.open("POST", urlRemover, true);
  xhttpRemover.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
  xhttpRemover.send(paramsRemover);
}


/* 
********** end agendamento ************
*/


/* 
********** login ************
*/


// Função para listar todas as farmácias com vacinação da gripe
function listarFarmaciasGripe() {
  var xhttp = new XMLHttpRequest();
  xhttp.onreadystatechange = function () {
    if (this.readyState == 4 && this.status == 200) {
      const obj = JSON.parse(xhttp.responseText);
      farmacia.farmacias = obj.farmacias; 
      console.log(farmacia.farmacias);
    }
    let target = document.getElementById("farmacia-lista2");
    target.innerHTML = "";

    for (let farmaciaItem of farmacia.farmacias) {

      var farmaciaInfo = document.createElement("p");
      farmaciaInfo.innerHTML = `ID: ${farmaciaItem.entity_id} Nome: ${farmaciaItem.name} Local: ${farmaciaItem.postal_code_locality}`;
      target.appendChild(farmaciaInfo);

    }
  };
  xhttp.open("GET", `https://magno.di.uevora.pt/tweb/t1/program/gripe/list`, true);
  xhttp.send();
}

// Função para listar todas as farmácias com vacinação para COVID-19
function listarFarmaciasCovid() {
  var xhttp = new XMLHttpRequest();
  xhttp.onreadystatechange = function () {
    if (this.readyState == 4 && this.status == 200) {
      const obj = JSON.parse(xhttp.responseText);
      farmacia.farmacias = obj.farmacias; 
      console.log(farmacia.farmacias);
    }
    let target = document.getElementById("farmacia-lista2");
    target.innerHTML = "";

    for (let farmaciaItem of farmacia.farmacias) {

      var farmaciaInfo = document.createElement("p");
      farmaciaInfo.innerHTML = `ID: ${farmaciaItem.entity_id} Nome: ${farmaciaItem.name} Local: ${farmaciaItem.postal_code_locality}`;
      target.appendChild(farmaciaInfo);

    }
  };
  xhttp.open("GET", `https://magno.di.uevora.pt/tweb/t1/program/covid19/list`, true);
  xhttp.send();
}

// Função para adicionar uma farmácia
function adicionarFarmacia() {
  let request = new XMLHttpRequest();
  let url = "https://magno.di.uevora.pt/tweb/t1/program/add";

  request.onreadystatechange = function () {
    if (request.readyState === 4) {
      if (request.status === 200) {
        const data = JSON.parse(request.responseText);
        console.log(data);
      } else {
        console.error("Erro ao adicionar farmácia:", request.statusText);
      }
    }
  };

  let newNome = document.getElementById('addNome').value;
  let checkGripe = document.getElementById('add-gripe');
  let checkCovid = document.getElementById('add-covid');
  let services = [];

  if (checkCovid.checked) {
    services.push('covid-19');
  }

  if (checkGripe.checked) {
    services.push('gripe');
  }

  if (services.length > 0) {
    request.open("POST", url, true);
    request.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    request.send(`name=${encodeURIComponent(newNome)}&services=${encodeURIComponent(services.join(','))}`);
  } else {
    alert("Por favor, selecione pelo menos um serviço.");
  }
}

// Função para remover uma farmácia
function removerFarmacia() {
  let request = new XMLHttpRequest();
  let url = "https://magno.di.uevora.pt/tweb/t1/program/remove";

  request.onreadystatechange = function () {
    if (request.readyState === 4) {
      if (request.status === 200) {
        const data = JSON.parse(request.responseText);
        console.log(data);
      } else {
        console.error("Erro ao remover farmácia:", request.statusText);
      }
    }
  };

  let checkGripe = document.getElementById('remove-gripe');
  let checkCovid = document.getElementById('remove-covid');
  let nomeToRemove = document.getElementById('removeNome').value;

  let servicesToRemove = [];

  if (checkCovid.checked) {
    servicesToRemove.push('covid-19');
  }

  if (checkGripe.checked) {
    servicesToRemove.push('gripe');
  }

  if (servicesToRemove.length > 0) {
    request.open("POST", url, true);
    request.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    request.send(`name=${encodeURIComponent(nomeToRemove)}&services=${encodeURIComponent(servicesToRemove.join(','))}`);
  } else {
    alert("Por favor, selecione pelo menos um serviço para remover.");
  }
}


function getGraph() {
  const obj = farmacia;
  let localidadesCount = {};

  for (let farmacias of obj.farmacias) {
    const localidade = farmacias.postal_code_locality;
    localidadesCount[localidade] = (localidadesCount[localidade] || 0) + 1;
  }

  const localidades = Object.keys(localidadesCount);
  const numFarmacias = localidades.map(localidade => localidadesCount[localidade]);

  // Define os dados
  const data = [{
    x: localidades,
    y: numFarmacias,
    type: 'bar'
  }];

  // Define o layout
  const layout = {
    xaxis: { title: 'Localidade' },
    yaxis: { title: 'Número de Farmácias' },
    title: 'Número de Farmácias por Localidade'
  };

  Plotly.newPlot('g1', data, layout);
}
/* 
********** end login ************
*/