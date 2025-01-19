import { fetchDebtors, saveDebtorService, updateDebtorService } from './services/apiDebtorService.js';
import { loadDebtsByDebtor, updateDebt } from './services/apiDebtService.js';
import { displayDebtorInfo, createRowDebtor, getDebtorFormAttributes } from './debtor.js';
import { displayDebtInfo, getDebtAttributes } from './debt.js';
import { maskCPF, maskPhone , formatCurrency } from './formatter.js';

document.addEventListener('DOMContentLoaded', function () {
  document.getElementById('debt_date').max = new Date().toISOString().split("T")[0];
  const listDebtors = document.getElementById('list-debtors-tbody');
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
});

function showAddDebtor(){
  displayDebtorInfo(null);
  const debts_container = document.getElementById('debts-container');
  debts_container.style.display = 'none';
}

function addNewDebt(){
  displayDebtInfo();
}

function saveDebtor() {
  const errorMessage = document.getElementById('error-message');
  if (errorMessage) {
    errorMessage.style.display = 'none';
  }
  const debtor = getDebtorFormAttributes();
  if (debtor == null) {
    return;
  }else{
    if(debtor.id){
      updateDebtorService(debtor).then(data => {
        if (data) {
          window.location.reload();
        }
      }).catch(error => console.error(error));
    }else{
      saveDebtorService(debtor).then(data => {
        if (data.response_code === '201') {
          window.location.reload();
        }else{
          window.alert(data.response_message);
        }
      }).catch(error => console.error(error));
    }
  }
  
}

function saveDebt(){
  const debt = getDebtAttributes();
  if(debt.id_debt !== null){
    updateDebt(debt).then(data => {
      if (data) {
        fetchDebtsByDebtor(debt.id_debtor);
      }
    }).catch(error => console.error(error));
  }else{
    saveDebtService(debt).then(data => {
      if (data.response_code === '201') {
        fetchDebtsByDebtor(debt.id_debtor);
      }else{
        console.error('Erro ao processar a resposta da API');
      }
    }).catch(error => console.error(error));
  }
  closeModal('modal-debt-info');
}

function fetchDebtsByDebtor(uid){
  const tbody = document.getElementById('list-debts-tbody');
  tbody.innerHTML = '';
  loadDebtsByDebtor(uid).then(data => {
    if (data) {
      displayDebts(data.response.debts);
    }
  }).catch(error => console.error(error));
}

function closeModal(id){
  const modal = document.getElementById(id);
  modal.style.display = 'none';
}

window.showAddDebtor = showAddDebtor;
window.addNewDebt = addNewDebt;
window.saveDebtor = saveDebtor;
window.saveDebt = saveDebt;
window.maskCPF = maskCPF;
window.maskPhone = maskPhone;
window.formatCurrency = formatCurrency;