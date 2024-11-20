document.addEventListener('DOMContentLoaded', function () {
    const urlParams = new URLSearchParams(window.location.search);
    const uid = urlParams.get('uid');

    if (uid) {

        const headers = {
            'Content-Type': 'application/json',
            'id': uid
        };

        fetch(`/api/debtors/fetch_debtor`, { method: 'GET', headers: headers })
            .then(response => response.json())
            .then(data => {
                if (data.response && data.response.debtor) {
                    exibirInformacoesUsuario(data.response.debtor);
                } else {
                    console.error('Erro ao processar a resposta da API');
                }
            });
    } else {
        console.error('Usuário não encontrado na url');
        window.close();
    }
});

function exibirInformacoesUsuario(usuario) {
    const nome = document.getElementById('nome');
    const email = document.getElementById('email');
  
    nome.textContent = usuario.name;
    email.textContent = usuario.email;
  
    modal.style.display = 'block';
  }