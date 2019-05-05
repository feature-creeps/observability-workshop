### known Bugs

### to be fixed
* traceId is not always propagated (noticed with orchestrator)

#### to be refined
* imageresize changes background color to black on some png's (suppose some alpha/transparency layer thingy)
* imagerotate slightly moves image to the right when rotating. very noticeable with higher degrees number.
* imagegrayscale cannot handle the images it creates itself: grayscale + persist -> grayscale again -> fail

#### embraced

* resize/rotate cannot handle jpg
* image size larger than 10mb fails