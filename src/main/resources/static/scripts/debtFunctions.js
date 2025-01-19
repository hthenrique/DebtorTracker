import { loadDebtById, loadDebtsByDebtor, createDebt, updateDebt, deleteDebtById } from './services/apiDebtService.js';
import { displayDebtInfo, createRowDebt } from './debt.js';

export function saveDebt() {
  const debt = getDebtAttributes();
  if (debt.id_debt !== null) {
    updateDebt(debt).then(data => {
      if (data) {
        fetchDebtsByDebtor(debt.id_debtor);
      }
    }).catch(error => console.error(error));
  } else {
    createDebt(debt).then(data => {
      if (data) {
        fetchDebtsByDebtor(debt.id_debtor);
      } else {
        console.error('Erro ao processar a resposta da API');
      }
    }).catch(error => console.error(error));
  }
}

export function editDebt(row) {
  const editButton = row.querySelector('.btn.btn-edit.edit-debt');
  editButton.addEventListener('click', (e) => {
    e.preventDefault();
    const id = editButton.getAttribute('data-uid');
    loadDebtById(id).then(data => {
      if (data) {
        displayDebtInfo(data);
      }
    }).catch(error => console.error(error));
  });
}

export function deleteDebt(row) {
  const deleteButton = row.querySelector('.btn.btn-danger.btn-delete.delete-debt');
  deleteButton.addEventListener('click', (e) => {
    e.preventDefault();
    const id = deleteButton.getAttribute('data-uid');
    deleteDebtById(id).then(data => {
      if (data) {
        window.location.reload();
      }
    }).catch(error => console.error(error));
  });
}

export function fetchDebtsByDebtor(uid) {
  const tbody = document.getElementById('list-debts-tbody');
  console.log('Clearing existing rows in tbody');
  tbody.innerHTML = '';
  loadDebtsByDebtor(uid).then(data => {
    if (data) {
      displayDebts(data);
    }
  }).catch(error => console.error(error));
}

export function createButtonEdit(uid, className) {
  const button = document.createElement('button');
  button.id = 'btn-edit';
  button.className = className;
  button.textContent = 'Editar';
  button.setAttribute('data-uid', uid);
  return button;
}