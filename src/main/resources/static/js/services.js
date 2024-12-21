function fetchDebtors() {
    return fetch('/api/debtors/fetch_debtors')
        .then(response => response.json())
        .then(data => {
            if (data.response && data.response.debtors) {
                return data.response.debtors;
            } else {
                console.error('Erro ao processar a resposta da API');
                return [];
            }
        })
        .catch(error => {
            console.error(error);
            return [];
        });
}

function loadDebtorById(uid) {
    if (uid) {
        const headers = {
            'Content-Type': 'application/json',
            'id': uid
        };

        return fetch(`/api/debtors/fetch_debtor`, { method: 'GET', headers: headers })
            .then(response => response.json())
            .then(data => {
                if (data.response && data.response.debtor) {
                    return data.response.debtor;
                } else {
                    console.error('Erro ao processar a resposta da API');
                    return null;
                }
            })
            .catch(error => {
                console.error(error);
                return null;
            });
    } else {
        return Promise.reject('UID is required');
    }
}