export const emailValidation = email => {
    const regex = /^[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,3}$/;;
    return regex.test(email);
}

// 영문 소문자 또는 대문자, 숫자, 특수문자 포함 8~15자 
export const pwdValidation = pwd => {
    const regex = /^(?=.*[a-zA-Z])(?=.*[!@#$%^*+=-])(?=.*[0-9]).{8,15}/;
    return regex.test(pwd);
}