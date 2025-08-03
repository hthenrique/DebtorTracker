import { fetchDebtors, fetchDebtorsByAttribute, saveDebtorService, updateDebtorService } from './services/apiDebtorService.js';
import { deleteDebtor } from './services/apiDebtorService.js';
import { createDebt, loadDebtsByDebtor, updateDebt } from './services/apiDebtService.js';
import { displayDebtorInfo, createRowDebtor, getDebtorFormAttributes } from './debtor.js';
import { displayDebtInfo, getDebtAttributes } from './debt.js';
import { maskCPF, maskPhone, formatCurrency } from './formatter.js';

document.addEventListener('DOMContentLoaded', function () {
  document.getElementById('debt_date').max = new Date().toISOString().split("T")[0];
  defaultDebtorsList();
});

function showAddDebtor() {
  displayDebtorInfo(null);
  const debts_container = document.getElementById('debts-container');
  debts_container.style.display = 'none';
}

function addNewDebt() {
  displayDebtInfo(null);
}

function saveDebtor() {
  const errorMessage = document.getElementById('error-message');
  if (errorMessage) {
    errorMessage.style.display = 'none';
  }
  const debtor = getDebtorFormAttributes();
  if (debtor == null) {
    return;
  } else {
    if (debtor.id) {
      updateDebtorService(debtor).then(data => {
        if (data) {
          window.location.reload();
        }
      }).catch(error => console.error(error));
    } else {
      saveDebtorService(debtor).then(data => {
        if (data.response_code === '201') {
          window.location.reload();
        } else {
          window.alert(data.response_message);
        }
      }).catch(error => console.error(error));
    }
  }

}

function searchDebtor(value) {

  if (!value || value.trim() === '') {
    defaultDebtorsList();
    return;
  }

  const input = value.trim();

  let type = null;

  // CPF: apenas números, 11 dígitos
  if ( /^\d+$/.test(value) || /^\d{11}$/.test(input)) {
    type = 'cpf';
  }

  // Se contiver '@', é e-mail
  if (input.includes('@')) {
    // Validação opcional de formato de e-mail
    if (/^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(input)) {
      type = 'email';
    } else {
      console.log('Formato de e-mail inválido');
      return;
    }
  }
  
  // Nome: apenas letras (com ou sem acento, pode ter espaços)
  if (/^[A-Za-zÀ-ÿ\s]+$/.test(input)) {
    type = 'nome';
  } 
  
  if (!type) {
    console.log('Formato inválido para busca');
    return;
  }

  console.log('Tipo detectado:', type);

  fetchDebtorsByAttribute(value, type).then(data => {
    const response_code = data.response_code;
    if (response_code == '200') {
      const debtors = data.response.debtors;
      const listDebtors = document.getElementById('list-debtors-tbody');
      listDebtors.innerHTML = '';
      if (Array.isArray(debtors)) {
        debtors.forEach(debtor => {
          listDebtors.appendChild(createRowDebtor(debtor));
        });
      } else {
        console.error('Expected an array of debtors');
      }
    }
    if (response_code === '404') {
      const listDebtors = document.getElementById('list-debtors-tbody');
      listDebtors.innerHTML = '';
    }
  }).catch(error => console.error(error));
}

function deleteDebtorProfile() {
  const debtor = getDebtorFormAttributes();
  if (debtor == null) {
    return;
  } else {
    if (debtor.id) {
      deleteDebtor(debtor.id).then(() => {
        window.location.reload();
      }).catch(error => console.error(error));
    }
  }
}

function saveDebt() {
  const debt = getDebtAttributes();
  if (debt.id_debt !== null) {
    updateDebt(debt).then(data => {
      if (data) {
        fetchDebtsByDebtor(debt.id_debtor);
      }
    }).catch(error => console.error(error));
  } else {
    createDebt(debt).then(data => {
      if (data.response_code === '201') {
        fetchDebtsByDebtor(debt.id_debtor);
      } else {
        console.error('Erro ao processar a resposta da API');
      }
    }).catch(error => console.error(error));
  }
  closeModal('modal-debt-info');
}

function fetchDebtsByDebtor(uid) {
  const tbody = document.getElementById('list-debts-tbody');
  tbody.innerHTML = '';
  loadDebtsByDebtor(uid).then(data => {
    if (data) {
      displayDebts(data.response.debts);
    }
  }).catch(error => console.error(error));
}

function closeModal(id) {
  const modal = document.getElementById(id);
  modal.style.display = 'none';
}

function defaultDebtorsList() {
  const listDebtors = document.getElementById('list-debtors-tbody');
  listDebtors.innerHTML = '';
  fetchDebtors().then(data => {
    const debtors = data.response.debtors;
    if (Array.isArray(debtors)) {
      debtors.forEach(debtor => {
        listDebtors.appendChild(createRowDebtor(debtor));
      });
    } else {
      console.error('Expected an array of debtors');
    }
  }).catch(error => console.error(error));
}

window.showAddDebtor = showAddDebtor;
window.addNewDebt = addNewDebt;
window.deleteDebtorProfile = deleteDebtorProfile;
window.saveDebtor = saveDebtor;
window.saveDebt = saveDebt;
window.maskCPF = maskCPF;
window.maskPhone = maskPhone;
window.formatCurrency = formatCurrency;
window.searchDebtor = searchDebtor;