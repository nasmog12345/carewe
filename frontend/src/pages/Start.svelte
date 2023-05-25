
<style>
  .cards-container {
    display: flex;
    justify-content: space-around;
    flex-wrap: wrap;
    margin: 20px;
  }

  .card {
    max-width: 500px;
    margin: 20px;
  }
  
  .box {
    display: flex;
    justify-content: center;
    align-items: center;
    width: 100%;
    height: 250px;
  }

  .image {
    width: 100%;
    height: 100%;
  }

  .image img {
    max-width: 100%;
    max-height: 100%;
    object-fit: cover;
  }

  a {
    display: block;
    width: 100%;
    height: 100%;
  }

  .card-header {
    background-color: white;
  }
</style>

<h3 style="text-align: center;">Willkommen zu CareWe! </h3>
<h3 style="text-align: center;">Ich will...</h3>

<div class="cards-container">
  <div class="card">
    <h1 class="card-header">gerne helfen</h1>
    <a href="/#/needyhome">
      <div class="box card-body">
        <div class="image">
          <img src="/bilder/3.png" alt="Helper" class="card-img-top">
        </div>
      </div>
    </a>
  </div>

  <div class="card">
    <h1 class="card-header">Hilfe beanspruchen</h1>
    <a href="/#/helperhome">
      <div class="box card-body">
        <div class="image">
          <img src="/bilder/2.png" alt="Needy" class="card-img-top">
        </div>
      </div>
    </a>
  </div>
</div>
