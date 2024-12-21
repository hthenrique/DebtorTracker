function displayDebtorInfo(debtor) {
    const nome = document.getElementById('nome');
    const cpf = document.getElementById('cpf');
    const email = document.getElementById('email');
    const phone = document.getElementById('phone');

    if (nome && cpf && email && phone) {
        nome.value = debtor.name;
        cpf.value = debtor.docNumber;
        email.value = debtor.email;
        phone.value = debtor.phone_number;

        maskCPF({ target: cpf });
        maskPhone({ target: phone });

        const title = document.getElementById('info-debtor-title');
        title.textContent = `Informações de ${debtor.name}`;

        const modal = document.getElementById('modal');
        modal.style.display = 'block';

        modal.addEventListener('click', function (event) {
            if (event.target === modal) {
                modal.style.display = 'none';
            }
        });
    } else {
        console.error('Elementos do formulário não encontrados');
    }
}