export const environment = {
  production: true,
  backend: {
    imageorchestrator: '/proxy/imageorchestrator',
    imageholder: '/proxy/imageholder',
    imagerotator: '/proxy/imagerotator',
    imagegrayscale: '/proxy/imagegrayscale',
    imageresize: '/proxy/imageresize',
    imageflip: '/proxy/imageflip',
    imagethumbnail: '/proxy/imagethumbnail'
  },
  tools: {
    kibana: 'kibana.o11y.fans',
    grafana: 'kibana.o11y.fans',
    prometheus: 'prometheus.o11y.fans',
    jaeger: 'jaeger.o11y.fans',
    kiali: 'kiali.o11y.fans',
  }
};
