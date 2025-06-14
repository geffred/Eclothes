document.addEventListener("DOMContentLoaded", function () {
  // Carousel functionality
  const carouselSlides = document.querySelectorAll(".carousel-slide");
  const dotsContainer = document.querySelector(".carousel-dots");
  let currentSlide = 0;

  // Create dots
  carouselSlides.forEach((_, index) => {
    const dot = document.createElement("span");
    dot.classList.add("dot");
    if (index === 0) dot.classList.add("active");
    dot.addEventListener("click", () => goToSlide(index));
    dotsContainer.appendChild(dot);
  });

  const dots = document.querySelectorAll(".dot");

  // Next/previous controls
  document
    .querySelector(".carousel-control.next")
    .addEventListener("click", nextSlide);
  document
    .querySelector(".carousel-control.prev")
    .addEventListener("click", prevSlide);

  // Auto slide change
  let slideInterval = setInterval(nextSlide, 5000);

  function nextSlide() {
    goToSlide(
      currentSlide === carouselSlides.length - 1 ? 0 : currentSlide + 1
    );
  }

  function prevSlide() {
    goToSlide(
      currentSlide === 0 ? carouselSlides.length - 1 : currentSlide - 1
    );
  }

  function goToSlide(index) {
    carouselSlides[currentSlide].classList.remove("active");
    dots[currentSlide].classList.remove("active");

    currentSlide = index;

    carouselSlides[currentSlide].classList.add("active");
    dots[currentSlide].classList.add("active");

    // Reset timer when manually changing slides
    clearInterval(slideInterval);
    slideInterval = setInterval(nextSlide, 5000);
  }

  // Pause on hover
  const carousel = document.querySelector(".hero-carousel");
  carousel.addEventListener("mouseenter", () => clearInterval(slideInterval));
  carousel.addEventListener(
    "mouseleave",
    () => (slideInterval = setInterval(nextSlide, 5000))
  );
});
