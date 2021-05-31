const form = document.getElementById("mainForm");
const username = document.getElementById("userName");
const last = document.getElementById("last");
const email = document.getElementById("email");
const password = document.getElementById("pass");
const password2 = document.getElementById("pass2");

// const passwordIn = document.getElementById("passForSecondPage");
// const emailIn = document.getElementById("emailForSecondPage");

form.addEventListener("submit", (item) => {
    item.preventDefault();

    checkInputs();
});

function checkInputs() {
    const usernameValue = username.value.trim();
    const emailValue = email.value.trim();
    const passwordValue = password.value.trim();
    const password2Value = password2.value.trim();
    const lastValue = last.value.trim();

    // const passwordSignInValue = passwordIn.value.trim();
    // const emailSignInValue = emailIn.value.trim();

    if (usernameValue === "") {
        setErrorFor(username, "Username cannot be blank");
    } else if (!isUsername(usernameValue)) {
        setErrorFor(username, "Not a valid username");
    } else {
        setSuccessFor(username);
    }


    if (lastValue === "") {
        setErrorFor(last, "Last name cannot be blank");
    } else if (!isLastName(lastValue)) {
        setErrorFor(last, "Not a valid last name");
    } else {
        setSuccessFor(last);
    }

    if (emailValue === "") {
        setErrorFor(email, "Email cannot be blank");
    } else if (!isEmail(emailValue)) {
        setErrorFor(email, "Not a valid email");
    } else {
        setSuccessFor(email);
    }

    // if (emailSignInValue === "") {
    //   setErrorFor(emailIn, "Email cannot be blank");
    // } else if (!isEmail(emailSignInValue)) {
    //   setErrorFor(emailIn, "Not a valid email");
    // } else {
    //   setSuccessFor(emailIn);
    // }

    if (passwordValue === "") {
        setErrorFor(password, "Password cannot be blank");
    } else if (!isPassword(passwordValue)) {
        setErrorFor(password, "Not a valid password");
    } else {
        setSuccessFor(password);
    }

    // if (passwordSignInValue === "") {
    //   setErrorFor(passwordIn, "Password cannot be blank");
    // } else if (!isPassword(passwordSignInValue)) {
    //   setErrorFor(passwordIn, "Not a valid password");
    // } else {
    //   setSuccessFor(passwordIn);
    // }

    if (password2Value === "") {
        setErrorFor(password2, "This cell cannot be blank");
    } else if (!isPass2(password2Value)) {
        setErrorFor(password2, "Check your password again");
    } else {
        setSuccessFor(password2);
    }
}

//
function setErrorFor(input, message) {
    const formControl = input.parentElement;
    const p = document.getElementsByTagName("p");
    formControl.className = "form-control error";
    p.innerText = message;
}

function setSuccessFor(input) {
    const formControl = input.parentElement;
    formControl.className = "form-control success";
}

function isEmail(email) {
    return /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/.test(
        email
    );
}

function isPass2(password2) {
    return /^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{8,15}$/.test(password2);
}


function isPassword(passwordIn) {
    return /^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{8,15}$/.test(passwordIn);
}

function isUsername(username) {
    return /^[a-zA-Z0-9]([._-](?![._-])|[a-zA-Z0-9]){2,18}[a-zA-Z0-9]$/.test(username);
}

function isLastName(last) {
    return /^[a-zA-Z0-9]([._-](?![._-])|[a-zA-Z0-9]){2,18}[a-zA-Z0-9]$/.test(last);
}

function validate() {
    let x = document.getElementById("pass")[0].value,
        y = document.getElementById("pass2")[0].value;
    if (x === y) {
        return true;
    }
    return false;
}

//
// function openAlreadySignInPage() {
//   display = document.getElementById("alreadySignInPage").style.display;
//   if (display == "none") {
//     document.getElementById("alreadySignInPage").style.display = "block";
//     document.getElementById("mainBlock").style.display = "none";
//   } else {
//     document.getElementById("mainBlock").style.display = "block";
//     document.getElementById("alreadySignInPage").style.display = "none";
//   }
// }

function openProfilePage() {
    let display = document.getElementById("profileBlock").style.display;

    if (display == "none") {
        document.getElementById("profileBlock").style.display = "block";
        document.getElementById("mainBlock").style.display = "none";
        document.getElementById("alreadySignInPage").style.display = "none";
    } else {
        document.getElementById("mainBlock").style.display = "block";
        document.getElementById("profileBlock").style.display = "none";
        document.getElementById("alreadySignInPage").style.display = "none";
    }
}


