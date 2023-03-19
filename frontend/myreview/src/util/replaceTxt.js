// hmtl 태그 제거
export const replaceTxt = (str) =>{
    return str.replace(/(<([^>]+)>)/ig,"");
}