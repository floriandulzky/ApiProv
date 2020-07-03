<template>
  <div
    class="auth-layout-wrap"
    :style="{ backgroundImage: 'url(' + bgImage + ')' }"
  >
    <div class="auth-content">
      <div class="card o-hidden">
        <div class="row">
          <div class="col-md-12">
            <div class="p-4">
              <div class="auth-logo text-center mb-30">
                <img :src="logo" alt="" />
              </div>
              <h1 class="mb-3 text-18">Forgot Password</h1>
              <form action="">
                <div class="form-group">
                  <label for="email">Email address</label>
                  <input
                    id="email"
                    class="form-control form-control-rounded"
                    type="email"
                  />
                </div>
                <button class="btn btn-primary btn-block btn-rounded mt-3">
                  Reset Password
                </button>
              </form>
              <div class="mt-3 text-center">
                <router-link to="signIn" tag="a" class="text-muted ">
                  <u>Sign In</u>
                </router-link>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
export default {
  metaInfo: {
    // if no subcomponents specify a metaInfo.title, this title will be used
    title: "Forgot Password"
  },
  data() {
    return {
      bgImage: require("@/assets/images/photo-wide-4.jpg"),
      logo: require("@/assets/images/logo.png"),
      formImage: require("@/assets/images/photo-long-3.jpg")
    };
  }
};
</script>
