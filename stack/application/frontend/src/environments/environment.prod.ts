export const environment = {
  production: true,
  backend: {
    imageorchestrator: '/proxy/imageorchestrator',
    imageholder: '/proxy/imageholder',
    imagerotator: '/proxy/imagerotator',
    imagegrayscale: '/proxy/imagegrayscale',
    imageresize: '/proxy/imageresize',
    imageflip: '/proxy/imageflip',
    imagethumbnail: '/proxy/imagethumbnail',
    trafficgen: '/proxy/trafficgen'
  },
  tools: {
    kibana: 'https://kibana.o11y.fans',
    grafana: 'https://grafana.o11y.fans',
    prometheus: 'https://prometheus.o11y.fans',
    jaeger: 'https://jaeger.o11y.fans',
    kiali: 'https://kiali.o11y.fans',
  }
};
