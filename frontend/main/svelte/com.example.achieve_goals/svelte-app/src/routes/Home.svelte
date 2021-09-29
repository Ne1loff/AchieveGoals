<script lang="ts">
    import {navigate} from "svelte-routing";
    import Header from "./Header.svelte";

    let avatarUrl = ["/static/img_avatar.png", "/static/img_avatar2.png"]

    let user = {
        username: '',
        name: '',
        surname: '',
        male: '',
        email: '',
        locality: '',
    };


    fetch('/api/user')
        .then(response => {
            console.log(response.status)
            if (response.status === 200)
                return response.json()
            else {
                navigate('/login')
            }
        })
        .then(commit => {
            user = commit
            console.log(user)
        })
    //todo enter with Enter key
</script>


<Header {user}/>


<style>

</style>