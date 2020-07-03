export default (to, from, next) => {
  if (
    localStorage.getItem("userInfo") != null &&
    localStorage.getItem("userInfo").length > 0
  ) {
    next("/app/pages/blank");
  } else {
    localStorage.removeItem("userInfo");
    next("/app/sessions/signIn");
  }
};
