import json
import os
import requests
from requests.packages.urllib3.exceptions import InsecureRequestWarning

from locust import HttpUser, task, between, tag

POSTAL_POSTALCODE_URL = '/address/postalcode'


def add_option_queries(url: str, **options) -> str:
    """URLにoptionのqueryを付与して返す
    """
    if not options:
        raise ValueError('Option is missing.')

    queries = []
    for option, value in options.items():
        queries.append(option + '=' + value)
    url += '?' + '&'.join(queries)

    return url


def make_payload(obj: dict):
    """json形式でpayloadを返す
    """
    return json.dumps(obj).encode('utf-8')


class CommonMethod():
    # SSLチェックを無視して出力されるInsecureRequestWarningメッセージをdisable
    requests.packages.urllib3.disable_warnings(InsecureRequestWarning)

    header = {
        'Content-Type': 'application/json'
    }

    def send_get(self, url):
        return self.client.get(url, headers=self.header, verify=False)

    def send_post(self, url, payload):
        return self.client.post(
            url,
            data=payload,
            headers=self.header,
            verify=False)


class PostalcodeUser(HttpUser, CommonMethod):
    """郵便番号データ利用者のテストケース
    """
    wait_time = between(1, 2)

    @tag('postalcode')
    @task
    def postalcode_code_easy(self):
        """郵便番号サービスに単純な住所コード検索の負荷をかける
        """
        api_url = POSTAL_POSTALCODE_URL + '/1100001'
        self.send_get(api_url)


"""
    @tag('postalcode')
    @task
    def postalcode_code_hard(self):
        # 郵便番号サービスに複雑な住所コード検索の負荷をかける
        api_url = add_option_queries(
            POSTAL_POSTALCODE_URL + '/1100001', limit='100', kana='full'
        )
        self.send_get(api_url)
"""
