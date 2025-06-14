document.addEventListener("DOMContentLoaded", function () {
  // Initialize EmailJS with your user ID
  emailjs.init("YOUR_EMAILJS_USER_ID"); // Remplacez par votre User ID EmailJS

  const contactForm = document.getElementById("contactForm");
  const formMessage = document.getElementById("form-message");

  contactForm.addEventListener("submit", function (e) {
    e.preventDefault();

    // Show loading state
    const submitBtn = contactForm.querySelector('button[type="submit"]');
    submitBtn.disabled = true;
    submitBtn.textContent = "Envoi en cours...";

    // Send email using EmailJS
    emailjs
      .sendForm("YOUR_EMAILJS_SERVICE_ID", "YOUR_EMAILJS_TEMPLATE_ID", this)
      .then(
        function () {
          // Show success message
          formMessage.textContent =
            "Message envoyé avec succès! Nous vous répondrons dès que possible.";
          formMessage.classList.remove("error");
          formMessage.classList.add("success");

          // Reset form
          contactForm.reset();
          submitBtn.disabled = false;
          submitBtn.textContent = "Envoyer le message";

          // Hide message after 5 seconds
          setTimeout(() => {
            formMessage.classList.remove("success");
            formMessage.textContent = "";
          }, 5000);
        },
        function (error) {
          // Show error message
          formMessage.textContent =
            "Une erreur est survenue. Veuillez réessayer plus tard.";
          formMessage.classList.remove("success");
          formMessage.classList.add("error");

          submitBtn.disabled = false;
          submitBtn.textContent = "Envoyer le message";

          console.error("EmailJS Error:", error);
        }
      );
  });
});
