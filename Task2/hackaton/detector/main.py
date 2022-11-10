import re
from selenium import webdriver
from selenium.webdriver.common.by import By
from selenium.webdriver.chrome.options import Options


def look_for_style(href, folder):
    with open(folder + href, "r") as file:
        if href.find("headers") != -1:
            print("File " + href + " is corectly detected")
            return
        data = file.read()
        idx = data.find("h2")
        if idx == -1:
            idx = data.find("H2")
            if idx == -1:
                print("File " + href + " does not have any h2 styles")
                return
        close_idx = 0
        for i in range(idx, len(data)):
            if data[i] == '}':
                close_idx = i
                break
        raw = data[idx:close_idx]
        if raw.find('color') != -1:
            print("File " + href + " contains H2 color!")
            return


def search():
    print("Enter a path to the .html file:\n(For example: D:/Prog_pet_projects/HackatonBeCoder/hackaton/):")
    site_href = input()
    options = Options()
    options.add_argument('--headless')
    driver = webdriver.Chrome(options=options, executable_path="chromedriver.exe")

    driver.get("file://" + site_href + "index.html")
    headers = driver.find_elements(By.TAG_NAME, 'h2')
    page = driver.page_source
    css_find = [m.start() for m in re.finditer('\.css\">', page)]
    hrefs = []
    for css in css_find:
        href = ""
        idx = css
        while page[idx] != '"':
            href += page[idx]
            idx -= 1
        href = href[::-1]
        href += "css"
        hrefs.append(href)
    for file in hrefs:
        look_for_style(file, site_href)
    return


if __name__ == "__main__":
    search()
