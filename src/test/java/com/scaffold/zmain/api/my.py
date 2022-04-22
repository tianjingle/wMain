import sys

class CoreJava:

    def draw(self,code):
        print(code)


if __name__ == '__main__':
    codes = []
    print(len(sys.argv))
    for i in range(1, len(sys.argv)):
        codes.append(sys.argv[i])
    print(codes)
    corejava = CoreJava()
    corejava.draw(codes[0])