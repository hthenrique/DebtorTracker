document.addEventListener('DOMContentLoaded', function () {
  const listaUsuarios = document.getElementById('list-debtors-tbody');
  fetchDebtors().then(debtors => {
    if (debtors) {
      debtors.forEach(debtor => {
        const row = criarLinhaDebtor(debtor);
        listaUsuarios.appendChild(row);
      });
    }
  }).catch(error => console.error(error));
});

function criarLinhaDebtor(debtor) {
  const row = document.createElement('tr');
  row.innerHTML = `
    <td>${debtor.id}</td>
    <td>${debtor.name}</td>
    <td>${maskCPF(debtor.docNumber)}</td>
    <td>${debtor.email}</td>
    <td>${maskPhone(debtor.phone_number)}</td>
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
    // const url = `/debtor.html?uid=${uid}`;
    // window.open(url, '_blank');
    debtorInfo(uid);
  });
}

function debtorInfo(uid) {
  loadDebtorById(uid).then(debtor => {
    if (debtor) {
      displayDebtorInfo(debtor);
      const modal = document.getElementById('modal');
      modal.style.display = 'block';
    }
  }).catch(error => console.error(error));
}

function criarBotaoEditar(uid) {
  const button = document.createElement('button');
  button.className = 'btn btn-edit';
  button.textContent = 'Editar';
  button.setAttribute('data-uid', uid);
  button.addEventListener('click', (e) => {
    e.preventDefault();
    const url = `/debtor.html?uid=${uid}`;
    window.open(url, '_blank');
  });
  return button;
}