<!-- HelperCard.svelte -->
<script>
  export let helper;
  export let displayCard = false;

 //implement rating function and get request
function toggle(){
  displayCard = !displayCard;
}
console.log(helper.id)
</script>

<div class="card">
  <a href={`#/helper/${helper.id}`}>
  <h3>{helper.name}</h3>
</a>

        <button on:click={toggle}> Click for more</button>
    {#if displayCard}
    <img src={helper.picture} alt={helper.name}>
  <div class="details">
    <p>Email: {helper.email}</p>
    <p>Skills: {helper.skills.join(", ")}</p>
    <p>Matching Status: {helper.helperState}</p>
  </div>
  {/if}
</div>
