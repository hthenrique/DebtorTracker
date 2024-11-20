document.addEventListener('DOMContentLoaded', function () {
  const listaUsuarios = document.getElementById('lista-usuarios-tbody');

  fetch('/api/debtors/fetch_debtors')
    .then(response => response.json())
    .then(data => {
      if (data.response && data.response.debtors) {
        const debtors = data.response.debtors;
        debtors.forEach(debtor => {
          const row = criarLinhaDebtor(debtor);
          listaUsuarios.appendChild(row);
          return;
        });
      } else {
        console.error('Erro ao processar a resposta da API');
      }
    })
    .catch(error => console.error(error));
});

function criarLinhaDebtor(debtor) {
  const row = document.createElement('tr');
  row.innerHTML = `
    <td>${debtor.id}</td>
    <td>${debtor.name}</td>
    <td>${debtor.email}</td>
    <td>${debtor.phone_number}</td>
    <td class="actions">
      ${criarBotaoEditar(debtor.id).outerHTML}
      <button class="btn btn-danger">Excluir</button>
    </td>
  `;
  eventEditButtonClick(row);
  return row;
}

function eventEditButtonClick(row) {
  const editarButton = row.querySelector('.btn.btn-edit');
  editarButton.addEventListener('click', (e) => {
    e.preventDefault();
    const uid = editarButton.getAttribute('data-uid');
    const url = `/user.html?uid=${uid}`;
    window.open(url, '_blank');
  });
}

function criarBotaoEditar(uid) {
  const button = document.createElement('button');
  button.className = 'btn btn-edit';
  button.textContent = 'Editar';
  button.setAttribute('data-uid', uid);
  button.addEventListener('click', (e) => {
    e.preventDefault();
    const url = `/user.html?uid=${uid}`;
    window.open(url, '_blank');
  });
  return button;
}