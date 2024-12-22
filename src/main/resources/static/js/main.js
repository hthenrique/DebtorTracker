document.addEventListener('DOMContentLoaded', function () {
  const listDebtors = document.getElementById('list-debtors-tbody');
  fetchDebtors().then(debtors => {
    if (debtors) {
      debtors.forEach(debtor => {
        listDebtors.appendChild(createRowDebtor(debtor));
      });
    }
  }).catch(error => console.error(error));
});

function createRowDebtor(debtor) {
  const row = document.createElement('tr');
  row.innerHTML = `
    <td>${debtor.id}</td>
    <td>${debtor.name}</td>
    <td>${maskCPF(debtor.docNumber)}</td>
    <td>${debtor.email}</td>
    <td>${maskPhone(debtor.phone_number)}</td>
    <td class="actions">
      ${createButtonEdit(debtor.id).outerHTML}
      ${createButtonDelete(debtor.id).outerHTML}
    </td>
  `;
  eventEditButtonClick(row);
  eventDeleteButtonClick(row);
  return row;
}


function eventEditButtonClick(row) {
  const editButton = row.querySelector('.btn.btn-edit');
  editButton.addEventListener('click', (e) => {
    e.preventDefault();
    const uid = editButton.getAttribute('data-uid');
    let debtor = null;
    let debts = null;

    loadDebtorById(uid).then(data => {
      if (data) {
        debtor = data;
        displayDebtorInfo(debtor);
      }
    }).catch(error => console.error(error));

    loadDebtsByDebtor(uid).then(data => {
      if (data) {
        debts = data;
        displayDebts(debts);
      }
    }).catch(error => console.error(error));
  });
}

function eventDeleteButtonClick(row) {
  const deleteButton = row.querySelector('.btn.btn-danger.btn-delete');
  deleteButton.addEventListener('click', (e) => {
    e.preventDefault();
    const uid = deleteButton.getAttribute('data-uid');
    deleteDebtor(uid).then(data => {
      if (data) {
        window.location.reload();
      }
    }).catch(error => console.error(error));
  });
}


function createButtonEdit(uid) {
  const button = document.createElement('button');
  button.id = 'btn-edit';
  button.className = 'btn btn-edit';
  button.textContent = 'Editar';
  button.setAttribute('data-uid', uid);
  return button;
}

function createButtonDelete(uid) {
  const button = document.createElement('button');
  button.id = 'btn-delete';
  button.className = 'btn btn-danger btn-delete';
  button.textContent = 'Excluir';
  button.setAttribute('data-uid', uid);
  return button;
}