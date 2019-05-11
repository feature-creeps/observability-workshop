import {ApplicationRef, ComponentFactoryResolver, EmbeddedViewRef, Injectable, Injector} from '@angular/core';


@Injectable()
export class PreviewService {

  constructor(private componentFactoryResolver: ComponentFactoryResolver,
              private appRef: ApplicationRef,
              private injector: Injector) {
  }

  appendComponentToBody(component: any, id: string) {
    // Create a component reference from the component
    const componentRef = this.componentFactoryResolver
      .resolveComponentFactory(component)
      .create(this.injector);

    componentRef.instance.showImage(id)

    // Attach component to the appRef so that it's inside the ng component tree
    this.appRef.attachView(componentRef.hostView);

    // Get DOM element from component
    const domElem = (componentRef.hostView as EmbeddedViewRef<any>)
      .rootNodes[0] as HTMLElement;

    // Append DOM element to the body
    document.querySelectorAll("#album")[0].appendChild(domElem);

    // Wait some time and remove it from the component tree and from the DOM
    //setTimeout(() => {
    //  this.appRef.detachView(componentRef.hostView);
    //  componentRef.destroy();
    //}, 3000);
  }
}
