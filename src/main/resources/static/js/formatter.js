function maskCPF(event) {
    let value = null;
    if(event.target != null){
        value = event.target.value.replace(/\D/g, '');
    }else{
        value = event;
    }

    value = value.replace(/(\d{3})(\d)/, '$1.$2');
    value = value.replace(/(\d{3})(\d)/, '$1.$2');
    value = value.replace(/(\d{3})(\d{1,2})$/, '$1-$2');

    if(event.target != null){
        event.target.value = value;
    }else{
        return value;
    }
}

function maskPhone(event) {
    let value = null;
    if(event.target != null){
        value = event.target.value.replace(/\D/g, '');
    }else{
        value = event;
    }
    value = value.replace(/(\d{2})(\d)/, '($1) $2');
    value = value.replace(/(\d{5})(\d{1,4})$/, '$1-$2');
    if(event.target != null){
        event.target.value = value;
    }else{
        return value;
    }
}