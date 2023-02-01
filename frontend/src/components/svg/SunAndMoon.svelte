<svg class="sun-and-moon" aria-hidden="true" width="24" height="24" viewBox="0 0 24 24">
    <circle class="sun" cx="12" cy="12" r="6" mask="url(#moon-mask)" fill="currentColor"/>
    <g class="sun-beams" stroke="currentColor">
        <line x1="12" y1="1" x2="12" y2="3"></line>
        <line x1="12" y1="21" x2="12" y2="23"></line>
        <line x1="4.22" y1="4.22" x2="5.64" y2="5.64"></line>
        <line x1="18.36" y1="18.36" x2="19.78" y2="19.78"></line>
        <line x1="1" y1="12" x2="3" y2="12"></line>
        <line x1="21" y1="12" x2="23" y2="12"></line>
        <line x1="4.22" y1="19.78" x2="5.64" y2="18.36"></line>
        <line x1="18.36" y1="5.64" x2="19.78" y2="4.22"></line>
    </g>
    <mask class="moon" id="moon-mask">
        <rect x="0" y="0" width="100%" height="100%" fill="white"></rect>
        <circle cx="24" cy="10" r="6" fill="black"></circle>
    </mask>
</svg>

<style lang="scss">

  :root {
    --size: 2rem;
    --icon-fill: hsl(210 10% 30%);
    --icon-fill-hover: hsl(210 10% 15%);
  }

  .sun-and-moon {
    & > :is(.moon, .sun, .sun-beams) {
      transform-origin: center center;
    }

    & > :is(.moon, .sun) {
      fill: var(--icon-fill);

      .theme-toggle:is(:hover, :focus-visible) > & {
        fill: var(--icon-fill-hover);
      }
    }

    & > .sun-beams {
      stroke: var(--icon-fill);
      stroke-width: 2px;

      .theme-toggle:is(:hover, :focus-visible) & {
        stroke: var(--icon-fill-hover);
      }
    }

    :global([data-theme="dark"]) & {
      & > .sun {
        transform: scale(1.75);
      }

      & > .sun-beams {
        opacity: 0;
      }

      & > .moon > circle {
        transform: translateX(-7px);

        @supports (cx: 1) {
          transform: translateX(0);
          cx: 17;
        }
      }
    }

    @media (prefers-reduced-motion: no-preference) {
      & > .sun {
        transition: transform .5s var(--ease-elastic-3);
      }

      & > .sun-beams {
        transition: transform .5s var(--ease-elastic-4),
        opacity .5s var(--ease-3);
      }

      & .moon > circle {
        transition: transform .25s var(--ease-out-5);

        @supports (cx: 1) {
          transition: cx .25s var(--ease-out-5);
        }
      }

      :global([data-theme="dark"]) & {
        & > .sun {
          transform: scale(1.75);
          transition-timing-function: var(--ease-3);
          transition-duration: .25s;
        }

        & > .sun-beams {
          transform: rotateZ(-25deg);
          transition-duration: .15s;
        }

        & > .moon > circle {
          transition-delay: .25s;
          transition-duration: .5s;
        }
      }
    }
  }
</style>