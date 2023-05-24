

<style>
  .box {
    display: flex;
    justify-content: space-around; /* Use space-around to give some space between the images */
    align-items: center;
    width: 100%;
    height: 100vh;
    background-color: lightgray;
  }

  .image {
    width: 45%; /* Adjust the width so two images can fit side by side */
    height: 100%;
  }

  .image img {
    width: 100%;
    height: 100%;
    object-fit: cover;
  }

  a {
    display: block;
    width: 100%;
    height: 100%;
  }
</style>

<h1 style="text-align: center;">Ich bin...</h1>

<div class="box">
  <div class="image">
    <h1>Hilfsbedürftiger</h1>
    <a href="/#/needyhome">
      <img src="/bilder/2.png" alt="Needy">
    </a>
  </div>
  
  <div class="image">
    <h1>Helfer</h1>
    <a href="/#/helperhome">
      <img src="/bilder/3.png" alt="Helper">
    </a>
  </div>
</div>
